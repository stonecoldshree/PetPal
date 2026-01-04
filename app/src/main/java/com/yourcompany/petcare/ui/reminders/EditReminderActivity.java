package com.yourcompany.petcare.ui.reminders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.yourcompany.petcare.App;
import com.yourcompany.petcare.R;
import com.yourcompany.petcare.data.model.Pet;
import com.yourcompany.petcare.data.model.Reminder;
import com.yourcompany.petcare.ui.viewmodels.PetViewModel;
import com.yourcompany.petcare.ui.viewmodels.ReminderViewModel;
import com.yourcompany.petcare.notifications.ReminderScheduler;

import java.util.ArrayList;
import java.util.List;

public class EditReminderActivity extends AppCompatActivity {

    private Spinner petSpinner;
    private EditText inputTitle, inputNotes, inputDateTime;
    private Switch switchRecurring;
    private Button btnPickTone, btnSave;
    private ReminderViewModel remVM;
    private PetViewModel petVM;
    private List<Pet> petList = new ArrayList<>();
    private ActivityResultLauncher<Intent> tonePicker;
    private Uri chosenToneUri;
    private Reminder current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);

        petSpinner = findViewById(R.id.petSpinner);
        inputTitle = findViewById(R.id.inputTitle);
        inputNotes = findViewById(R.id.inputNotes);
        inputDateTime = findViewById(R.id.inputDateTime);
        switchRecurring = findViewById(R.id.switchRecurring);
        btnPickTone = findViewById(R.id.btnPickTone);
        btnSave = findViewById(R.id.btnSaveReminder);

        remVM = new ViewModelProvider(this).get(ReminderViewModel.class);
        petVM = new ViewModelProvider(this).get(PetViewModel.class);

        long reminderId = getIntent().getLongExtra("reminderId", -1);

        petVM.getPets().observe(this, pets -> {
            petList = pets != null ? pets : new ArrayList<>();
            String[] names = new String[petList.size() + 1];
            names[0] = "No pet";
            for (int i = 0; i < petList.size(); i++) names[i + 1] = petList.get(i).getName();
            petSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names));
            populateIfReady(reminderId);
        });

        remVM.getRemindersForOwner(App.getCurrentUserEmail()).observe(this, list -> {
            if (list == null) return;

            for (Reminder r : list) {
                if (r.getId() == reminderId) {
                    current = r;
                    populateIfReady(reminderId);
                    break;
                }
            }
        });


        tonePicker = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), ar -> {
            if (ar.getResultCode() == RESULT_OK && ar.getData() != null) {
                chosenToneUri = ar.getData().getParcelableExtra(android.media.RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            }
        });

        btnPickTone.setOnClickListener(v -> {
            Intent i = new Intent(android.media.RingtoneManager.ACTION_RINGTONE_PICKER);
            i.putExtra(android.media.RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);
            i.putExtra(android.media.RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
            tonePicker.launch(i);
        });

        btnSave.setOnClickListener(v -> saveReminder());
    }

    private void populateIfReady(long reminderId) {
        if (current == null || petList == null) return;

        inputTitle.setText(current.getTitle());
        inputNotes.setText(current.getNotes());
        inputDateTime.setText(String.valueOf(current.getDateTimeMillis()));
        switchRecurring.setChecked(current.isRecurringDaily());
        if (current.getSoundUri() != null) chosenToneUri = Uri.parse(current.getSoundUri());

        if (current.getPetId() == 0) petSpinner.setSelection(0);
        else {
            for (int i = 0; i < petList.size(); i++) {
                if (petList.get(i).getId() == current.getPetId()) {
                    petSpinner.setSelection(i + 1);
                    break;
                }
            }
        }
    }

    private void saveReminder() {
        if (current == null) return;

        String title = inputTitle.getText().toString().trim();
        String notes = inputNotes.getText().toString().trim();

        if (notes.isEmpty()) {
            inputNotes.setError("Required");
            inputNotes.requestFocus();
            return;
        }

        long dt = 0;
        try {
            dt = Long.parseLong(inputDateTime.getText().toString().trim());
        } catch (Exception e) {
            inputDateTime.setError("Provide epoch millis");
            inputDateTime.requestFocus();
            return;
        }

        current.setTitle(title.isEmpty() ? "Untitled" : title);
        current.setNotes(notes);
        current.setDateTimeMillis(dt);
        current.setRecurringDaily(switchRecurring.isChecked());
        current.setSoundUri(chosenToneUri == null ? null : chosenToneUri.toString());

        int sel = petSpinner.getSelectedItemPosition();
        if (sel <= 0) current.setPetId(0);
        else current.setPetId(petList.get(sel - 1).getId());

        remVM.update(current);
        ReminderScheduler.scheduleReminder(this, current.getId(), current.getDateTimeMillis());
        setResult(RESULT_OK);
        finish();
    }
}
