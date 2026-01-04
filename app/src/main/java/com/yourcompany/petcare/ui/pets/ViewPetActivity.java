package com.yourcompany.petcare.ui.pets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.model.Pet;
import com.yourcompany.petcare.ui.viewmodels.PetViewModel;

public class ViewPetActivity extends AppCompatActivity {

    private PetViewModel vm;
    private Pet currentPet;

    private TextView name, speciesTxt, breed, gender, age;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet);

        long petId = getIntent().getLongExtra("petId", -1);

        vm = new ViewModelProvider(this).get(PetViewModel.class);

        name = findViewById(R.id.viewName);
        speciesTxt = findViewById(R.id.viewType);  // Using your existing ID
        breed = findViewById(R.id.viewBreed);
        gender = findViewById(R.id.viewGender);
        age = findViewById(R.id.viewAge);
        avatar = findViewById(R.id.viewAvatar);

        MaterialButton editBtn = findViewById(R.id.btnEditPet);

        vm.getPets().observe(this, pets -> {
            if (pets == null) return;

            for (Pet p : pets) {
                if (p.getId() == petId) {
                    currentPet = p;
                    updateUI(p);
                    break;
                }
            }
        });

        editBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, EditPetActivity.class);
            i.putExtra("petId", petId);
            startActivity(i);
        });
    }

    private void updateUI(Pet p) {
        name.setText(p.getName());
        speciesTxt.setText(p.getSpecies());
        gender.setText(p.getGender());
        breed.setText(p.getBreed());
        age.setText(String.valueOf(p.getAge()));

        String species = p.getSpecies() == null ? "" : p.getSpecies().toLowerCase();

        switch (species) {
            case "dog":
                avatar.setImageResource(R.drawable.dog_avatar);
                break;
            case "cat":
                avatar.setImageResource(R.drawable.cat_avatar);
                break;
            case "bird":
                avatar.setImageResource(R.drawable.bird_avatar);
                break;
            default:
                avatar.setImageResource(R.drawable.other_avatar);
        }
    }
}
