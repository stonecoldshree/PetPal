package com.yourcompany.petcare.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.model.Reminder;
import com.yourcompany.petcare.ui.ai.AiChatActivity;
import com.yourcompany.petcare.ui.viewmodels.PetViewModel;
import com.yourcompany.petcare.ui.viewmodels.ReminderViewModel;

import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        TextView petsCount = v.findViewById(R.id.homePetsCount);
        TextView todaysCount = v.findViewById(R.id.homeTodayRemindersCount);
        TextView greeting = v.findViewById(R.id.homeGreeting);

        View aiCard = v.findViewById(R.id.homeAIAdvisorCard);

        PetViewModel petVM = new ViewModelProvider(requireActivity()).get(PetViewModel.class);
        ReminderViewModel remVM = new ViewModelProvider(requireActivity()).get(ReminderViewModel.class);

        // Logged-in user email
        String email = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getEmail()
                : null;

        // --- GREETING (Hello, Sujal 👋) ---
        if (email != null) {
            String name = email.split("@")[0];   // Basic name from email
            greeting.setText("Hello, " + name + " 👋");
        } else {
            greeting.setText("Hello 👋");
        }

        // --- PETS COUNT ---
        petVM.getPets().observe(getViewLifecycleOwner(), list -> {
            petsCount.setText(list != null ? String.valueOf(list.size()) : "0");
        });

        // --- TODAY REMINDERS COUNT ---
        if (email != null) {
            remVM.getRemindersForOwner(email).observe(getViewLifecycleOwner(), reminders -> {
                if (reminders == null) {
                    todaysCount.setText("0");
                    return;
                }
                todaysCount.setText(String.valueOf(getTodaysCount(reminders)));
            });
        }

        // --- AI ADVISOR CLICK ( THIS WAS THE ONLY THING MISSING ) ---
        aiCard.setOnClickListener(view -> {
            Intent i = new Intent(requireContext(), AiChatActivity.class);
            startActivity(i);
        });
    }

    private int getTodaysCount(List<Reminder> list) {
        Calendar now = Calendar.getInstance();

        int y = now.get(Calendar.YEAR);
        int m = now.get(Calendar.MONTH);
        int d = now.get(Calendar.DAY_OF_MONTH);

        int count = 0;

        for (Reminder r : list) {
            long t = r.getDateTimeMillis();
            if (t <= 0) continue;

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(t);

            if (c.get(Calendar.YEAR) == y &&
                    c.get(Calendar.MONTH) == m &&
                    c.get(Calendar.DAY_OF_MONTH) == d) {
                count++;
            }
        }
        return count;
    }
}
