package com.yourcompany.petcare.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.yourcompany.petcare.data.model.Reminder;

import java.util.List;

@Dao
public interface ReminderDao {

    @Insert
    long insert(Reminder r);

    @Update
    void update(Reminder r);

    @Delete
    void delete(Reminder r);

    @Query("SELECT * FROM reminders WHERE ownerEmail = :ownerEmail ORDER BY dateTimeMillis ASC")
    LiveData<List<Reminder>> getRemindersForUser(String ownerEmail);

    @Query("SELECT COUNT(*) FROM reminders WHERE ownerEmail = :ownerEmail AND dateTimeMillis <= :now")
    int countRemindersDone(String ownerEmail, long now);

    @Query("SELECT * FROM reminders WHERE id = :id LIMIT 1")
    Reminder getReminder(long id);

    @Query("UPDATE reminders SET done = 1 WHERE id = :id")
    void setDone(long id);

    // ⭐ REQUIRED FOR REPEATING REMINDERS
    @Query("UPDATE reminders SET dateTimeMillis = :nextEpoch WHERE id = :id")
    void updateTrigger(long id, long nextEpoch);
}
