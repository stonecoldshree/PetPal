package com.yourcompany.petcare.ui.ai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yourcompany.petcare.R;

import java.util.List;

public class AiChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMessage> list;

    public AiChatAdapter(List<ChatMessage> list) {
        this.list = list;
    }

    private static final int USER = 1;
    private static final int BOT = 2;

    @Override
    public int getItemViewType(int position) {
        return list.get(position).isUser() ? USER : BOT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == USER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_user, parent, false);
            return new UserVH(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_bot, parent, false);
            return new BotVH(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        ChatMessage m = list.get(pos);

        if (holder instanceof UserVH) ((UserVH) holder).msg.setText(m.getMessage());
        if (holder instanceof BotVH) ((BotVH) holder).msg.setText(m.getMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class UserVH extends RecyclerView.ViewHolder {
        TextView msg;
        UserVH(View v) {
            super(v);
            msg = v.findViewById(R.id.userMsg);
        }
    }

    static class BotVH extends RecyclerView.ViewHolder {
        TextView msg;
        BotVH(View v) {
            super(v);
            msg = v.findViewById(R.id.botMsg);
        }
    }
}
