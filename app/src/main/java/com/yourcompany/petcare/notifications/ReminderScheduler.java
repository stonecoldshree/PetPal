package com.yourcompany.petcare.notifications;

import android.content.Context;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public final class ReminderScheduler {

    private ReminderScheduler(){}

    private static String uniqueName(long id) { return "reminder_work_" + id; }

    public static void scheduleReminder(Context ctx, long reminderId, long timeMillis) {
        long now = System.currentTimeMillis();
        long delay = Math.max(0, timeMillis - now);

        Data input = new Data.Builder()
                .putLong(ReminderWorker.KEY_REMINDER_ID, reminderId)
                .build();

        OneTimeWorkRequest w = new OneTimeWorkRequest.Builder(ReminderWorker.class)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(input)
                .build();

        WorkManager.getInstance(ctx).enqueueUniqueWork(uniqueName(reminderId),
                androidx.work.ExistingWorkPolicy.REPLACE, w);
    }

    public static void cancelReminder(Context ctx, long reminderId) {
        WorkManager.getInstance(ctx).cancelUniqueWork(uniqueName(reminderId));
    }
}
