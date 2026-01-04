package com.yourcompany.petcare.ui.profile;

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
import com.yourcompany.petcare.ui.auth.LoginActivity;
import com.yourcompany.petcare.ui.settings.SettingsActivity;
import com.yourcompany.petcare.ui.viewmodels.PetViewModel;
import com.yourcompany.petcare.ui.viewmodels.ReminderViewModel;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

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
        statPets = v.findViewById(R.id.statPets);
        statRemDone = v.findViewById(R.id.statRemDone);

        ImageButton btnEdit = v.findViewById(R.id.btnEditProfile);
        Button btnSettings = v.findViewById(R.id.btnSettings);
        Button btnHelp = v.findViewById(R.id.btnHelp);
        Button btnAbout = v.findViewById(R.id.btnAbout);
        Button btnLogout = v.findViewById(R.id.btnLogout);
        Button btnDeleteAccount = v.findViewById(R.id.btnDeleteAccount);

        profileName.setText(prefs.getString("profile_name", "Your Name"));

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            profileEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        String img = prefs.getString("profile_image", null);
        if (img != null) profileAvatar.setImageURI(Uri.parse(img));

        btnSettings.setOnClickListener(x ->
                startActivity(new Intent(requireContext(), SettingsActivity.class)));

        btnHelp.setOnClickListener(x -> showHelpDialog());
        btnAbout.setOnClickListener(x -> showAboutDialog());

        btnLogout.setOnClickListener(x -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        });

        btnDeleteAccount.setOnClickListener(x -> deleteAccount());

        // ViewModels
        PetViewModel petVM = new ViewModelProvider(requireActivity()).get(PetViewModel.class);
        ReminderViewModel remVM = new ViewModelProvider(requireActivity()).get(ReminderViewModel.class);

        petVM.getPets().observe(getViewLifecycleOwner(),
                pets -> statPets.setText(String.valueOf(pets.size())));

        remVM.getRemindersForOwner(
                FirebaseAuth.getInstance().getCurrentUser().getEmail()
        ).observe(getViewLifecycleOwner(), rems -> {

            int done = 0;
            for (Reminder r : rems)
                if (r.isDone()) done++;

            statRemDone.setText(String.valueOf(done));
        });

        // Chart
        BarChart chart = v.findViewById(R.id.weeklyChart);
        setupChart(chart);

        remVM.getRemindersForOwner(
                FirebaseAuth.getInstance().getCurrentUser().getEmail()
        ).observe(getViewLifecycleOwner(), reminders -> {

            int[] week = new int[7];

            for (Reminder r : reminders) {
                long time = r.getDateTimeMillis();
                if (time <= 0) continue;

                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(time);

                int dow = c.get(Calendar.DAY_OF_WEEK);
                int idx = (dow + 5) % 7;
                week[idx]++;
            }

            updateChart(chart, week);
        });
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
