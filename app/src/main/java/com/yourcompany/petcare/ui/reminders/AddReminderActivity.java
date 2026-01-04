package com.yourcompany.petcare.ui.reminders;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.yourcompany.petcare.App;
import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.model.Pet;
import com.yourcompany.petcare.data.model.Reminder;
import com.yourcompany.petcare.notifications.ReminderScheduler;
import com.yourcompany.petcare.ui.viewmodels.PetViewModel;
import com.yourcompany.petcare.ui.viewmodels.ReminderViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AddReminderActivity extends AppCompatActivity {

    private EditText etTitle, etNotes, etDateTime;
    private AutoCompleteTextView petDropdown;
    private SwitchMaterial switchRepeat;
    private MaterialButton btnTone, btnSave;

    private ReminderViewModel vm;
    private PetViewModel petVm;

    private long selectedEpoch = 0;
    private String selectedToneUri = "";
    private long selectedPetId = 0;   // store petId (was selectedPetEmail before)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        vm = new ViewModelProvider(this).get(ReminderViewModel.class);
        petVm = new ViewModelProvider(this).get(PetViewModel.class);

        etTitle = findViewById(R.id.etTitle);
        etNotes = findViewById(R.id.etNotes);
        etDateTime = findViewById(R.id.etDateTime);
        petDropdown = findViewById(R.id.petDropdown);
        switchRepeat = findViewById(R.id.switchRepeatDaily);
        btnTone = findViewById(R.id.btnChooseTone);
        btnSave = findViewById(R.id.btnSaveReminder);

        setupPetDropdown();
        setupDateTimePicker();
        setupTonePicker();
        setupSaveReminder();
    }

    private void setupPetDropdown() {
        petVm.getPets().observe(this, pets -> {
            if (pets == null || pets.isEmpty()) {
                petDropdown.setAdapter(null);
                return;
            }

            String[] names = new String[pets.size()];
            for (int i = 0; i < pets.size(); i++) names[i] = pets.get(i).getName();

            petDropdown.setAdapter(new android.widget.ArrayAdapter<>(
                    this, android.R.layout.simple_dropdown_item_1line, names
            ));

            // show suggestions when focused
            petDropdown.setOnFocusChangeListener((v, has) -> {
                if (has) petDropdown.showDropDown();
            });

            petDropdown.setOnItemClickListener((parent, view, position, id) -> {
                Pet p = pets.get(position);
                selectedPetId = p.getId(); // store petId
            });
        });
    }

    private void setupDateTimePicker() {
        etDateTime.setOnClickListener(v -> {

            MaterialDatePicker<Long> datePicker =
                    MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Select Date")
                            .build();

            datePicker.show(getSupportFragmentManager(), "DATE_PICK");

            datePicker.addOnPositiveButtonClickListener(date -> {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(date);

                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTitleText("Select Time")
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(0)
                        .build();

                timePicker.show(getSupportFragmentManager(), "TIME_PICK");

                timePicker.addOnPositiveButtonClickListener(t -> {
                    cal.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    cal.set(Calendar.MINUTE, timePicker.getMinute());
                    cal.set(Calendar.SECOND, 0);

                    selectedEpoch = cal.getTimeInMillis();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
                    etDateTime.setText(sdf.format(cal.getTime()));
                });
            });
        });
    }

    private void setupTonePicker() {
        btnTone.setOnClickListener(v -> {
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);

            startActivityForResult(intent, 101);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) selectedToneUri = uri.toString();
        }
    }

    private void setupSaveReminder() {
        btnSave.setOnClickListener(v -> {

            if (selectedEpoch == 0) {
                Toast.makeText(this, "Pick a date & time", Toast.LENGTH_SHORT).show();
                return;
            }

            String userEmail = App.getCurrentUserEmail();
            if (userEmail == null) {
                Toast.makeText(this, "Not signed in", Toast.LENGTH_SHORT).show();
                return;
            }

            Reminder r = new Reminder(
                    etTitle.getText().toString(),
                    etNotes.getText().toString(),
                    userEmail,
                    selectedEpoch,
                    selectedToneUri,
                    switchRepeat.isChecked()
            );

            // attach pet id if selected
            r.setPetId(selectedPetId);

            vm.insert(r);

            // schedule using id is tricky because insert is async; schedule after small delay or let worker pick by DB later
            // best-effort: schedule using selectedEpoch and repository will set id; if you need strict, fetch latest reminder
            ReminderScheduler.scheduleReminder(this, r.getId(), selectedEpoch);
            Toast.makeText(this, "Reminder added!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
