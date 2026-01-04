package com.yourcompany.petcare.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.yourcompany.petcare.data.local.AppDatabase;
import com.yourcompany.petcare.data.local.PetDao;
import com.yourcompany.petcare.data.local.ReminderDao;
import com.yourcompany.petcare.data.local.UserDao;
import com.yourcompany.petcare.data.model.Pet;
import com.yourcompany.petcare.data.model.Reminder;
import com.yourcompany.petcare.data.model.UserProfile;

import java.util.List;
import java.util.concurrent.Executors;

public class AppRepository {

    private static AppRepository instance;
    private final UserDao userDao;
    private final PetDao petDao;
    private final ReminderDao reminderDao;

    private AppRepository(Context ctx) {
        AppDatabase db = AppDatabase.getInstance(ctx);
        userDao = db.userDao();
        petDao = db.petDao();
        reminderDao = db.reminderDao();
    }

    public static synchronized AppRepository getInstance(Context ctx) {
        if (instance == null)
            instance = new AppRepository(ctx.getApplicationContext());
        return instance;
    }

    // ----------------------
    // USER OPERATIONS
    // ----------------------
    public void createOrEnsureUser(String email) {
        Executors.newSingleThreadExecutor().execute(() -> {
            UserProfile u = userDao.getUserByEmail(email);
            if (u == null) {
                u = new UserProfile(email);
                userDao.insertUser(u);
            }
        });
    }

    public UserProfile getUser(String email) {
        return userDao.getUserByEmail(email);
    }

    public void upsertUser(UserProfile u) {
        Executors.newSingleThreadExecutor().execute(() -> userDao.insertUser(u));
    }

    // ----------------------
    // PET OPERATIONS
    // ----------------------
    public void addPet(Pet p) {
        Executors.newSingleThreadExecutor().execute(() -> petDao.insertPet(p));
    }

    public void updatePet(Pet p) {
        Executors.newSingleThreadExecutor().execute(() -> petDao.updatePet(p));
    }

    public void deletePet(Pet p) {
        Executors.newSingleThreadExecutor().execute(() -> petDao.deletePet(p));
    }

    public List<Pet> getPetsForUser(String email) {
        return petDao.getPetsForOwner(email);
    }

    // ----------------------
    // REMINDER OPERATIONS
    // ----------------------
    public void addReminder(Reminder r) {
        Executors.newSingleThreadExecutor().execute(() -> reminderDao.insert(r));
    }

    public void updateReminder(Reminder r) {
        Executors.newSingleThreadExecutor().execute(() -> reminderDao.update(r));
    }

    public void deleteReminder(Reminder r) {
        Executors.newSingleThreadExecutor().execute(() -> reminderDao.delete(r));
    }

    // FIXED: LiveData MUST be returned
    public LiveData<List<Reminder>> getRemindersForUser(String email) {
        return reminderDao.getRemindersForUser(email);
    }
}
