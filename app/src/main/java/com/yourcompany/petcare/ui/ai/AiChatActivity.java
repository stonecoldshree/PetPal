package com.yourcompany.petcare.ui.ai;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yourcompany.petcare.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AiChatActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private EditText input;
    private ImageButton send;

    private AiChatAdapter adapter;
    private final List<ChatMessage> messages = new ArrayList<>();

    private final Handler main = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_chat);

        recycler = findViewById(R.id.chatRecycler);
        input = findViewById(R.id.inputMessage);
        send = findViewById(R.id.sendBtn);

        adapter = new AiChatAdapter(messages);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        send.setOnClickListener(v -> {
            String text = input.getText().toString().trim();
            if (text.isEmpty()) return;

            addUserMessage(text);
            input.setText("");

            sendMessageToGemini(text);
        });
    }

    private void addUserMessage(String msg) {
        messages.add(new ChatMessage(msg, true));
        adapter.notifyItemInserted(messages.size() - 1);
        recycler.scrollToPosition(messages.size() - 1);
    }

    private void addBotMessage(String msg) {
        messages.add(new ChatMessage(msg, false));
        adapter.notifyItemInserted(messages.size() - 1);
        recycler.scrollToPosition(messages.size() - 1);
    }

    private void sendMessageToGemini(String userMessage) {

        System.setProperty("dns.server", "8.8.8.8"); // CRITICAL FIX FOR EMULATOR

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();


        String json = "{ \"contents\": [ { \"parts\": [ { \"text\": \"" +
                userMessage.replace("\"", "\\\"") +
                "\" } ] } ] }";

        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(GeminiApiService.API_URL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                main.post(() -> addBotMessage("Network error: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String raw = response.body().string();

                try {
                    JSONObject root = new JSONObject(raw);

                    String reply = root
                            .getJSONArray("candidates")
                            .getJSONObject(0)
                            .getJSONObject("content")
                            .getJSONArray("parts")
                            .getJSONObject(0)
                            .getString("text");

                    main.post(() -> addBotMessage(reply));

                } catch (Exception ex) {
                    main.post(() -> addBotMessage("Parsing error: " + ex.getMessage()));
                }
            }
        });
    }
}
