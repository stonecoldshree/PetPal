package com.yourcompany.petcare.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.yourcompany.petcare.App;
import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.model.Reminder;
import com.yourcompany.petcare.ui.adapters.ReminderListAdapter;
import com.yourcompany.petcare.ui.reminders.AddReminderActivity;
import com.yourcompany.petcare.ui.reminders.EditReminderActivity;
import com.yourcompany.petcare.ui.reminders.ViewReminderActivity;
import com.yourcompany.petcare.ui.viewmodels.ReminderViewModel;

public class RemindersFragment extends Fragment {

    public RemindersFragment() {
        super(R.layout.fragment_reminders);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        RecyclerView rv = v.findViewById(R.id.remindersRecycler);
        ExtendedFloatingActionButton fab = v.findViewById(R.id.addReminderFab);

        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        ReminderListAdapter adapter = new ReminderListAdapter(requireContext());
        rv.setAdapter(adapter);

        ReminderViewModel vm = new ViewModelProvider(requireActivity()).get(ReminderViewModel.class);

        // Try FirebaseAuth first (source of truth). Fallback to App if necessary.
        String email = null;
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if (email == null) email = App.getCurrentUserEmail();

        if (email != null) {
            vm.getRemindersForOwner(email).observe(getViewLifecycleOwner(), list -> {
                // adapter.setItems expects List<Reminder>
                adapter.setItems(list);
            });
        } else {
            // No signed-in user; clear adapter
            adapter.setItems(null);
        }

        fab.setOnClickListener(x ->
                startActivity(new Intent(requireContext(), AddReminderActivity.class)));

        adapter.setListener(new ReminderListAdapter.ReminderClickListener() {
            @Override
            public void onView(Reminder r) {
                Intent i = new Intent(requireContext(), ViewReminderActivity.class);
                i.putExtra("id", r.getId());
                startActivity(i);
            }

            @Override
            public void onEdit(Reminder r) {
                Intent i = new Intent(requireContext(), EditReminderActivity.class);
                i.putExtra("id", r.getId());
                startActivity(i);
            }

            @Override
            public void onDelete(Reminder r) {
                vm.delete(r);
            }

            @Override
            public void onDone(Reminder r) {
                vm.markAsDone(r.getId());
            }
        });
    }
}
