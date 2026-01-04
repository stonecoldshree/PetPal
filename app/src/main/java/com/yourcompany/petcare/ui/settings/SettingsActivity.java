package com.yourcompany.petcare.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.yourcompany.petcare.App;
import com.yourcompany.petcare.R;
import com.yourcompany.petcare.ui.auth.LoginActivity;

public class SettingsActivity extends AppCompatActivity {

    private Button btnLogout, btnDeleteAccount;
    private SwitchMaterial darkModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnLogout = findViewById(R.id.btnLogout);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        darkModeSwitch = findViewById(R.id.darkModeSwitch);

        // Load saved mode
        boolean isDark = getSharedPreferences("app_settings", MODE_PRIVATE)
                .getBoolean("dark_mode", false);

        darkModeSwitch.setChecked(isDark);

        // Apply current mode at startup
        AppCompatDelegate.setDefaultNightMode(
                isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        // Toggle listener
        darkModeSwitch.setOnCheckedChangeListener((buttonView, checked) -> {
            AppCompatDelegate.setDefaultNightMode(
                    checked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );

            // Save preference
            getSharedPreferences("app_settings", MODE_PRIVATE)
                    .edit()
                    .putBoolean("dark_mode", checked)
                    .apply();
        });


        btnLogout.setOnClickListener(v -> {
            App.setCurrentUserEmail(null);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        btnDeleteAccount.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete account")
                    .setMessage("This will remove your profile and reminders. Continue?")
                    .setPositiveButton("Delete", (d, w) -> {
                        App.setCurrentUserEmail(null);
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Remove About + Help
        View about = findViewById(R.id.btnAbout);
        if (about != null) about.setVisibility(View.GONE);
        View help = findViewById(R.id.btnHelp);
        if (help != null) help.setVisibility(View.GONE);
    }
}
