package com.yourcompany.petcare.ui.profile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yourcompany.petcare.App;
import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.repository.AppRepository;
import com.yourcompany.petcare.data.model.UserProfile;

import java.util.concurrent.Executors;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editName, editAge, editPhone;
    private Button btnSave;
    private AppRepository repo;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        repo = AppRepository.getInstance(getApplicationContext());

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSaveProfile);

        email = getIntent().getStringExtra("email");
        if (email == null) email = App.getCurrentUserEmail();

        // ------------------------------
        // FIX: Load profile OFF the UI thread
        // ------------------------------
        Executors.newSingleThreadExecutor().execute(() -> {
            UserProfile u = repo.getUser(email);

            runOnUiThread(() -> {
                if (u != null) {
                    editName.setText(u.getName());
                    editAge.setText(u.getAge() == 0 ? "" : String.valueOf(u.getAge()));
                    editPhone.setText(u.getPhone());
                }
            });
        });

        btnSave.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();

            int age = 0;
            try { age = Integer.parseInt(editAge.getText().toString().trim()); }
            catch (Exception ignored) {}

            String phone = editPhone.getText().toString().trim();

            if (email == null) {
                Toast.makeText(this, "Missing user email", Toast.LENGTH_SHORT).show();
                return;
            }

            UserProfile p = new UserProfile(email);
            p.setEmail(email);
            p.setName(name);
            p.setAge(age);
            p.setPhone(phone);

            // ------------------------------
            // FIX: Save profile OFF the UI thread (optional but recommended)
            // ------------------------------
            Executors.newSingleThreadExecutor().execute(() -> {
                repo.upsertUser(p);

                runOnUiThread(() -> {
                    App.setCurrentUserEmail(email);
                    Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
        });
    }
}
