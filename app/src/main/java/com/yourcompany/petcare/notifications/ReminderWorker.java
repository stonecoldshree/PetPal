package com.yourcompany.petcare.notifications;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.yourcompany.petcare.data.local.AppDatabase;
import com.yourcompany.petcare.data.local.ReminderDao;
import com.yourcompany.petcare.data.model.Reminder;

public class ReminderWorker extends Worker {

    public static final String KEY_REMINDER_ID = "reminder_id";
    private final ReminderDao dao;

    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        dao = AppDatabase.getInstance(context).reminderDao();
    }

    @NonNull
    @Override
    public Result doWork() {
        long id = getInputData().getLong(KEY_REMINDER_ID, -1);
        if (id == -1) return Result.failure();

        Reminder r = dao.getReminder(id);
        if (r == null) return Result.failure();

        NotificationHelper.showReminderNotification(getApplicationContext(),
                r.getId(), r.getTitle(), r.getNotes(), r.getSoundUri());

        if (r.isRecurringDaily()) {
            long next = r.getDateTimeMillis() + 24L*60*60*1000;
            dao.updateTrigger(r.getId(), next);
            ReminderScheduler.scheduleReminder(getApplicationContext(), r.getId(), next);
        } else {
            dao.setDone(r.getId());
        }

        return Result.success();
    }
}
