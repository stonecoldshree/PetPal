package com.yourcompany.petcare.ui.pets;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.model.Pet;
import com.yourcompany.petcare.ui.viewmodels.PetViewModel;

public class AddPetActivity extends AppCompatActivity {

    private PetViewModel vm;
    private TextInputEditText nameInput, breedInput, ageInput;
    private ImageView avatarPreview;
    private AutoCompleteTextView speciesInput, genderInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        vm = new ViewModelProvider(this).get(PetViewModel.class);

        nameInput = findViewById(R.id.petNameInput);
        breedInput = findViewById(R.id.petBreedInput);
        ageInput = findViewById(R.id.petAgeInput);
        avatarPreview = findViewById(R.id.petAvatarPreview);

        speciesInput = findViewById(R.id.petSpeciesInput);
        genderInput  = findViewById(R.id.petGenderInput);

        setupDropdowns();

        MaterialButton saveBtn = findViewById(R.id.btnSavePet);
        saveBtn.setOnClickListener(v -> savePet());
    }

    private void setupDropdowns() {

        String[] species = {"dog", "cat", "bird", "other"};
        ArrayAdapter<String> speciesAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, species);
        speciesInput.setAdapter(speciesAdapter);

        String[] genders = {"male", "female"};
        ArrayAdapter<String> genderAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genders);
        genderInput.setAdapter(genderAdapter);

        speciesInput.setOnItemClickListener((parent, view, position, id) -> {
            String s = species[position];
            switch (s) {
                case "dog": avatarPreview.setImageResource(R.drawable.dog_avatar); break;
                case "cat": avatarPreview.setImageResource(R.drawable.cat_avatar); break;
                case "bird": avatarPreview.setImageResource(R.drawable.bird_avatar); break;
                default: avatarPreview.setImageResource(R.drawable.other_avatar);
            }
        });
    }

    private void savePet() {
        String name   = nameInput.getText().toString().trim();
        String breed  = breedInput.getText().toString().trim();
        String ageTxt = ageInput.getText().toString().trim();
        String species = speciesInput.getText().toString().trim();
        String gender  = genderInput.getText().toString().trim();

        if (name.isEmpty() || breed.isEmpty() || ageTxt.isEmpty()
                || species.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ensure user is signed in and we use their email as owner
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "You must be signed in to add a pet", Toast.LENGTH_SHORT).show();
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageTxt);
        } catch (NumberFormatException ex) {
            Toast.makeText(this, "Invalid age", Toast.LENGTH_SHORT).show();
            return;
        }

        String ownerEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if (ownerEmail == null) {
            Toast.makeText(this, "Unable to determine signed-in email", Toast.LENGTH_SHORT).show();
            return;
        }

        Pet pet = new Pet(name, species, gender, breed, ownerEmail, age);

        vm.insertPet(pet);
        Toast.makeText(this, "Pet Added!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
