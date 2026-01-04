package com.yourcompany.petcare.ui.reminders;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.model.Reminder;
import com.yourcompany.petcare.ui.viewmodels.ReminderViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;

public class ViewReminderActivity extends AppCompatActivity {

    private ReminderViewModel vm;
    private TextView tvTitle, tvNotes, tvWhen, tvPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);

        tvTitle = findViewById(R.id.viewTitle);
        tvNotes = findViewById(R.id.viewNotes);
        tvWhen = findViewById(R.id.viewWhen);
        tvPet = findViewById(R.id.viewPet);

        vm = new ViewModelProvider(this).get(ReminderViewModel.class);

        long id = getIntent().getLongExtra("id", -1);
        if (id != -1) loadReminder(id);
    }

    private void loadReminder(long id) {
        Executors.newSingleThreadExecutor().execute(() -> {

            Reminder r = vm.getReminderSync(id); // RUNNING IN BACKGROUND = SAFE

            if (r == null) return;

            runOnUiThread(() -> {
                tvTitle.setText(r.getTitle());
                tvNotes.setText(r.getNotes());

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(r.getDateTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
                tvWhen.setText(sdf.format(cal.getTime()));

                tvPet.setText(r.getPetId() == 0 ? "No Pet Selected" : ("Pet ID: " + r.getPetId()));
            });
        });
    }
}
