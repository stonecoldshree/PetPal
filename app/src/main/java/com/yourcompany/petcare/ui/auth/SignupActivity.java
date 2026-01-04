package com.yourcompany.petcare.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.yourcompany.petcare.App;
import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.repository.AppRepository;
import com.yourcompany.petcare.ui.profile.EditProfileActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnSignup;
    private AppRepository repo;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        repo = AppRepository.getInstance(getApplicationContext());
        auth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword); // Make sure you add this input in XML
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(v -> register());
    }

    private void register() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Enter email & password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(result -> {

                    // Save local user entry
                    repo.createOrEnsureUser(email);

                    // Save current user globally
                    App.setCurrentUserEmail(email);

                    Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(this, EditProfileActivity.class);
                    i.putExtra("email", email);
                    startActivity(i);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Signup failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
