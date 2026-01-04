package com.yourcompany.petcare.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.model.Reminder;
import com.yourcompany.petcare.data.model.UserProfile;
import com.yourcompany.petcare.data.repository.AppRepository;
import com.yourcompany.petcare.ui.auth.LoginActivity;
import com.yourcompany.petcare.ui.profile.EditProfileActivity;
import com.yourcompany.petcare.ui.settings.SettingsActivity;
import com.yourcompany.petcare.ui.viewmodels.PetViewModel;
import com.yourcompany.petcare.ui.viewmodels.ReminderViewModel;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    public ProfileFragment() { super(R.layout.fragment_profile); }

    private SharedPreferences prefs;
    private ImageView profileAvatar;
    private TextView profileName, profileEmail, statPets, statRemDone;

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        prefs = requireContext().getSharedPreferences("profile", MODE_PRIVATE);

        profileAvatar = v.findViewById(R.id.profileAvatar);
        profileName = v.findViewById(R.id.profileName);
        profileEmail = v.findViewById(R.id.profileEmail);
        // Load profile from database instead of only SharedPreferences
        new Thread(() -> {
            String email = FirebaseAuth.getInstance().getCurrentUser() != null
                    ? FirebaseAuth.getInstance().getCurrentUser().getEmail() : null;

            if (email != null) {
                UserProfile up = AppRepository.getInstance(requireContext()).getUser(email);
                if (up != null && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        profileName.setText(up.getName());
                    });
                }
            }
        }).start();

        statPets = v.findViewById(R.id.statPets);
        statRemDone = v.findViewById(R.id.statRemDone);

        ImageButton btnEdit = v.findViewById(R.id.btnEditProfile);
        Button btnSettings = v.findViewById(R.id.btnSettings);
        Button btnHelp = v.findViewById(R.id.btnHelp);
        Button btnAbout = v.findViewById(R.id.btnAbout);
        Button btnLogout = v.findViewById(R.id.btnLogout);
        Button btnDeleteAccount = v.findViewById(R.id.btnDeleteAccount);

        // hide Logout/Delete in Profile (they belong to Settings)
        btnLogout.setVisibility(View.GONE);
        btnDeleteAccount.setVisibility(View.GONE);

        profileName.setText(prefs.getString("profile_name", "Your Name"));

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            profileEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        else
            profileEmail.setText("Not signed in");

        String img = prefs.getString("profile_image", null);
        if (img != null) profileAvatar.setImageURI(Uri.parse(img));

        btnSettings.setOnClickListener(x -> {
            startActivity(new Intent(requireContext(), SettingsActivity.class));
        });

        btnHelp.setOnClickListener(x -> showHelpDialog());
        btnAbout.setOnClickListener(x -> showAboutDialog());

        // Edit profile -> try to open EditProfileActivity (preferred). Fallback to Settings.
        btnEdit.setOnClickListener(x -> {
            try {
                startActivity(new Intent(requireContext(), EditProfileActivity.class));
            } catch (Exception ex) {
                // fallback
                startActivity(new Intent(requireContext(), SettingsActivity.class));
            }
        });

        // ViewModels
        PetViewModel petVM = new ViewModelProvider(requireActivity()).get(PetViewModel.class);
        ReminderViewModel remVM = new ViewModelProvider(requireActivity()).get(ReminderViewModel.class);

        petVM.getPets().observe(getViewLifecycleOwner(),
                pets -> statPets.setText(String.valueOf(pets != null ? pets.size() : 0)));

        String email = FirebaseAuth.getInstance().getCurrentUser() != null ?
                FirebaseAuth.getInstance().getCurrentUser().getEmail() : null;

        // Chart
        BarChart chart = v.findViewById(R.id.weeklyChart);
        setupChart(chart);

        if (email != null) {
            remVM.getRemindersForOwner(email).observe(getViewLifecycleOwner(), reminders -> {
                int done = 0;
                int[] week = new int[7];
                if (reminders != null) {
                    for (Reminder r : reminders) {
                        if (r.isDone()) done++;
                        long time = r.getDateTimeMillis();
                        if (time <= 0) continue;
                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(time);
                        int dow = c.get(Calendar.DAY_OF_WEEK);
                        int idx = (dow + 5) % 7;
                        week[idx]++;
                    }
                }
                statRemDone.setText(String.valueOf(done));
                updateChart(chart, week);
            });
        } else {
            statRemDone.setText("0");
        }
    }

    private void showHelpDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Help")
                .setMessage("For support contact: support@petcare.com")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showAboutDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("About App")
                .setMessage("PetCare App © 2025\nMade with ❤ for pet lovers.")
                .setPositiveButton("OK", null)
                .show();
    }

    private void deleteAccount() {
        prefs.edit().clear().apply();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(requireContext(), LoginActivity.class));
        requireActivity().finish();
    }

    private void setupChart(BarChart chart) {
        chart.getDescription().setEnabled(false);
        chart.getAxisRight().setEnabled(false);

        XAxis x = chart.getXAxis();
        x.setGranularity(1f);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);

        x.setValueFormatter(new ValueFormatter() {
            final String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

            public String getAxisLabel(float value, XAxis axis) {
                int i = (int) value;
                return i >= 0 && i < 7 ? days[i] : "";
            }
        });
    }

    private void updateChart(BarChart chart, int[] data) {
        ArrayList<com.github.mikephil.charting.data.BarEntry> list = new ArrayList<>();

        for (int i = 0; i < 7; i++)
            list.add(new com.github.mikephil.charting.data.BarEntry(i, data[i]));

        com.github.mikephil.charting.data.BarDataSet set =
                new com.github.mikephil.charting.data.BarDataSet(list, "");

        set.setColor(getResources().getColor(R.color.purple_500));

        chart.setData(new com.github.mikephil.charting.data.BarData(set));
        chart.invalidate();
    }
}
