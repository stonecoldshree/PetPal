package com.yourcompany.petcare.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yourcompany.petcare.R;
import com.yourcompany.petcare.ui.fragments.HomeFragment;
import com.yourcompany.petcare.ui.fragments.PetsFragment;
import com.yourcompany.petcare.ui.fragments.RemindersFragment;
import com.yourcompany.petcare.ui.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_NOTIF_PERMISSION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestNotificationPermissionIfNeeded();

        BottomNavigationView nav = findViewById(R.id.bottomNav);

        nav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment;

                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    selectedFragment = new HomeFragment();

                } else if (id == R.id.menu_pets) {
                    selectedFragment = new PetsFragment();

                } else if (id == R.id.menu_reminders) {
                    selectedFragment = new RemindersFragment();

                } else if (id == R.id.menu_profile) {
                    selectedFragment = new ProfileFragment();

                } else {
                    selectedFragment = new HomeFragment(); // default fallback
                }

                // Replace fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, selectedFragment)
                        .commit();

                return true;
            }
        });

        // Load default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new HomeFragment())
                    .commit();
        }
    }

    /**
     * Android 13+ requires explicit POST_NOTIFICATIONS permission.
     */
    private void requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQ_NOTIF_PERMISSION
                );
            }
        }
    }

    // Optional handling if you want to show a toast or log
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_NOTIF_PERMISSION) {
            // You can add toast/log here if needed
        }
    }
}
