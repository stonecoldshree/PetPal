package com.yourcompany.petcare.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.model.Pet;
import com.yourcompany.petcare.ui.adapters.PetsListAdapter;
import com.yourcompany.petcare.ui.pets.AddPetActivity;
import com.yourcompany.petcare.ui.pets.EditPetActivity;
import com.yourcompany.petcare.ui.pets.ViewPetActivity;
import com.yourcompany.petcare.ui.viewmodels.PetViewModel;

import java.util.ArrayList;

public class PetsFragment extends Fragment {

    public PetsFragment() {
        super(R.layout.fragment_pets);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        RecyclerView rv = v.findViewById(R.id.petsRecycler);
        ExtendedFloatingActionButton addPetFab = v.findViewById(R.id.addPetFab);

        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        PetsListAdapter adapter = new PetsListAdapter(requireContext());
        rv.setAdapter(adapter);

        PetViewModel vm = new ViewModelProvider(requireActivity()).get(PetViewModel.class);

        // ⭐ FIX — reload pets for the currently logged-in account
        vm.loadPetsForCurrentUser();

        vm.getPets().observe(getViewLifecycleOwner(), list -> {
            adapter.setItems(list != null ? list : new ArrayList<>());
        });

        addPetFab.setOnClickListener(x ->
                startActivity(new Intent(requireContext(), AddPetActivity.class)));

        adapter.setListener(new PetsListAdapter.PetClickListener() {
            @Override
            public void onView(Pet pet) {
                Intent i = new Intent(requireContext(), ViewPetActivity.class);
                i.putExtra("petId", pet.getId());
                startActivity(i);
            }

            @Override
            public void onEdit(Pet pet) {
                Intent i = new Intent(requireContext(), EditPetActivity.class);
                i.putExtra("petId", pet.getId());
                startActivity(i);
            }

            @Override
            public void onDelete(Pet pet) {
                vm.deletePet(pet);
                Toast.makeText(requireContext(), "Pet deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
