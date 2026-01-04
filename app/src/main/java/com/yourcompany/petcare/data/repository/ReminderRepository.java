package com.yourcompany.petcare.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.yourcompany.petcare.data.local.AppDatabase;
import com.yourcompany.petcare.data.local.ReminderDao;
import com.yourcompany.petcare.data.model.Reminder;

import java.util.List;
import java.util.concurrent.Executors;

public class ReminderRepository {

    private final ReminderDao dao;

    public ReminderRepository(Context ctx) {
        dao = AppDatabase.getInstance(ctx).reminderDao();
    }

    public void insert(Reminder r) {
        Executors.newSingleThreadExecutor().execute(() -> dao.insert(r));
    }

    public void update(Reminder r) {
        Executors.newSingleThreadExecutor().execute(() -> dao.update(r));
    }

    public void delete(Reminder r) {
        Executors.newSingleThreadExecutor().execute(() -> dao.delete(r));
    }

    public LiveData<List<Reminder>> getRemindersForOwner(String email) {
        return dao.getRemindersForUser(email);
    }

    public Reminder getReminderSync(long id) {
        return dao.getReminder(id);
    }

    public void markAsDone(long id) {
        Executors.newSingleThreadExecutor().execute(() -> dao.setDone(id));
    }
}
