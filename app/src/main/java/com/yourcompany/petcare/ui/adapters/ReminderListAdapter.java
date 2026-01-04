package com.yourcompany.petcare.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.model.Reminder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.Holder> {

    public interface ReminderClickListener {
        void onView(Reminder r);
        void onEdit(Reminder r);
        void onDelete(Reminder r);
        void onDone(Reminder r);
    }

    private final Context ctx;
    private List<Reminder> items = new ArrayList<>();
    private ReminderClickListener listener;

    public ReminderListAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setItems(List<Reminder> list) {
        items = list;
        notifyDataSetChanged();
    }

    public void setListener(ReminderClickListener l) {
        listener = l;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(ctx).inflate(R.layout.item_reminder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int pos) {
        Reminder r = items.get(pos);

        h.title.setText(r.getTitle());
        h.subtitle.setText(r.getNotes());

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, hh:mm a");
        h.when.setText(sdf.format(new Date(r.getDateTimeMillis())));

        h.btnView.setOnClickListener(v -> listener.onView(r));
        h.btnEdit.setOnClickListener(v -> listener.onEdit(r));
        h.btnDelete.setOnClickListener(v -> listener.onDelete(r));

        // mark done action
        h.btnDone.setOnClickListener(v -> listener.onDone(r));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class Holder extends RecyclerView.ViewHolder {

        TextView title, subtitle, when;
        ImageButton btnView, btnEdit, btnDelete, btnDone;

        public Holder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.remTitle);
            subtitle = v.findViewById(R.id.remSubtitle);
            when = v.findViewById(R.id.remWhen);

            btnView = v.findViewById(R.id.btnViewRem);
            btnEdit = v.findViewById(R.id.btnEditRem);
            btnDelete = v.findViewById(R.id.btnDeleteRem);

            btnDone = new ImageButton(v.getContext());
            btnDone.setImageResource(R.drawable.ic_check);
        }
    }
}
