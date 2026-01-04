package com.yourcompany.petcare.ui.pets;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.model.Pet;
import com.yourcompany.petcare.ui.viewmodels.PetViewModel;

public class EditPetActivity extends AppCompatActivity {

    private PetViewModel vm;
    private Pet currentPet;

    private TextInputEditText editName;
    private TextInputEditText editBreed;
    private TextInputEditText editAge;
    private ImageView editImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);

        long petId = getIntent().getLongExtra("petId", -1);

        editName = findViewById(R.id.editPetName);
        editBreed = findViewById(R.id.editPetBreed);
        editAge = findViewById(R.id.editPetAge);
        editImage = findViewById(R.id.editPetPhoto);

        vm = new ViewModelProvider(this).get(PetViewModel.class);

        vm.getPets().observe(this, pets -> {

            if (pets == null) return;

            for (Pet p : pets) {
                if (p.getId() == petId) {
                    currentPet = p;
                    fillUI(p);
                    break;
                }
            }
        });

        MaterialButton save = findViewById(R.id.btnSaveEdit);
        save.setOnClickListener(v -> saveChanges());
    }

    private void fillUI(Pet p) {
        editName.setText(p.getName());
        editBreed.setText(p.getBreed());
        editAge.setText(String.valueOf(p.getAge()));

        // species → NOT type
        String species = p.getSpecies() == null ? "" : p.getSpecies().toLowerCase();

        switch (species) {
            case "dog":
                editImage.setImageResource(R.drawable.dog_avatar);
                break;
            case "cat":
                editImage.setImageResource(R.drawable.cat_avatar);
                break;
            case "bird":
                editImage.setImageResource(R.drawable.bird_avatar);
                break;
            default:
                editImage.setImageResource(R.drawable.other_avatar);
        }
    }

    private void saveChanges() {
        if (currentPet == null) return;

        String name = editName.getText().toString();
        String breed = editBreed.getText().toString();
        String ageTxt = editAge.getText().toString();

        if (name.isEmpty() || breed.isEmpty() || ageTxt.isEmpty()) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageTxt);

        currentPet.setName(name);
        currentPet.setBreed(breed);
        currentPet.setAge(age);

        vm.updatePet(currentPet);

        Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
