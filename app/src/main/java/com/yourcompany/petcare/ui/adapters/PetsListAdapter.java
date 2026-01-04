package com.yourcompany.petcare.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.model.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetsListAdapter extends RecyclerView.Adapter<PetsListAdapter.VH> {

    private final Context ctx;
    private final List<Pet> items = new ArrayList<>();

    public interface PetClickListener {
        void onView(Pet pet);
        void onEdit(Pet pet);
        void onDelete(Pet pet);
    }

    private PetClickListener listener;

    public void setListener(PetClickListener l) {
        this.listener = l;
    }

    // OLD Constructor
    public PetsListAdapter(Context ctx) {
        this.ctx = ctx;
    }

    // NEW Constructor (what you asked for)
    public PetsListAdapter(Context ctx, List<Pet> initialList) {
        this.ctx = ctx;
        if (initialList != null) items.addAll(initialList);
    }

    public void setItems(List<Pet> list) {
        items.clear();
        if (list != null) items.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_pet, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Pet p = items.get(position);
        holder.name.setText(p.getName());

        String details = p.getSpecies();
        if (p.getBreed() != null && !p.getBreed().isEmpty())
            details += " • " + p.getBreed();
        holder.breed.setText(details);

        switch (p.getSpecies().toLowerCase()) {
            case "dog": holder.avatar.setImageResource(R.drawable.dog_avatar); break;
            case "cat": holder.avatar.setImageResource(R.drawable.cat_avatar); break;
            case "bird": holder.avatar.setImageResource(R.drawable.bird_avatar); break;
            default: holder.avatar.setImageResource(R.drawable.other_avatar);
        }

        holder.btnView.setOnClickListener(v -> { if (listener != null) listener.onView(p); });
        holder.btnEdit.setOnClickListener(v -> { if (listener != null) listener.onEdit(p); });
        holder.btnDelete.setOnClickListener(v -> { if (listener != null) listener.onDelete(p); });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name, breed;
        ImageButton btnView, btnEdit, btnDelete;

        VH(@NonNull View v) {
            super(v);
            avatar = v.findViewById(R.id.petItemAvatar);
            name = v.findViewById(R.id.petItemName);
            breed = v.findViewById(R.id.petItemBreed);
            btnView = v.findViewById(R.id.btnView);
            btnEdit = v.findViewById(R.id.btnEdit);
            btnDelete = v.findViewById(R.id.btnDelete);
        }
    }
}
