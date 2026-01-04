package com.yourcompany.petcare.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminders")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    private String notes;
    private String ownerEmail;

    // Final structure for notifications + Worker
    private long dateTimeMillis;     // final timestamp for reminder
    private String soundUri;         // chosen ringtone uri
    private boolean recurringDaily;  // repeat daily toggle
    private boolean done;            // marked as completed

    private long petId;              // optional pet reference (kept for your structure)

    public Reminder(String title, String notes, String ownerEmail,
                    long dateTimeMillis, String soundUri,
                    boolean recurringDaily) {

        this.title = title;
        this.notes = notes;
        this.ownerEmail = ownerEmail;
        this.dateTimeMillis = dateTimeMillis;
        this.soundUri = soundUri;
        this.recurringDaily = recurringDaily;
        this.done = false;
        this.petId = 0; // default unless set
    }

    // ===== GETTERS =====

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getNotes() { return notes; }
    public String getOwnerEmail() { return ownerEmail; }

    public long getDateTimeMillis() { return dateTimeMillis; }
    public String getSoundUri() { return soundUri; }
    public boolean isRecurringDaily() { return recurringDaily; }
    public boolean isDone() { return done; }
    public long getPetId() { return petId; }

    // ===== SETTERS =====

    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }

    public void setDateTimeMillis(long dateTimeMillis) { this.dateTimeMillis = dateTimeMillis; }
    public void setSoundUri(String soundUri) { this.soundUri = soundUri; }
    public void setRecurringDaily(boolean recurringDaily) { this.recurringDaily = recurringDaily; }
    public void setDone(boolean done) { this.done = done; }
    public void setPetId(long petId) { this.petId = petId; }
}
