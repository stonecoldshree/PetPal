package com.yourcompany.petcare.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yourcompany.petcare.data.model.Pet;
import com.yourcompany.petcare.data.model.Reminder;
import com.yourcompany.petcare.data.model.UserProfile;

@Database(entities = {Pet.class, Reminder.class, UserProfile.class}, version =3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract PetDao petDao();
    public abstract ReminderDao reminderDao();
    public abstract UserDao userDao();

    public static AppDatabase getInstance(Context ctx) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(),
                                    AppDatabase.class, "petcare-db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
