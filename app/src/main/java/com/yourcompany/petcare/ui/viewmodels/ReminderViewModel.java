package com.yourcompany.petcare.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yourcompany.petcare.data.model.Reminder;
import com.yourcompany.petcare.data.repository.ReminderRepository;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel {

    private final ReminderRepository repo;

    public ReminderViewModel(@NonNull Application app) {
        super(app);
        repo = new ReminderRepository(app);
    }

    public LiveData<List<Reminder>> getRemindersForOwner(String email) {
        return repo.getRemindersForOwner(email);
    }

    public Reminder getReminderSync(long id) {
        return repo.getReminderSync(id);
    }

    public void insert(Reminder r) {
        repo.insert(r);
    }

    public void update(Reminder r) {
        repo.update(r);
    }

    public void delete(Reminder r) {
        repo.delete(r);
    }

    public void markAsDone(long id) {
        repo.markAsDone(id);
    }
}
