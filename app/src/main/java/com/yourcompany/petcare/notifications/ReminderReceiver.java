package com.yourcompany.petcare.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class ReminderReceiver extends BroadcastReceiver {

    public static final String EXTRA_REMINDER_ID = "reminder_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            long id = intent.getLongExtra(EXTRA_REMINDER_ID, -1);
            if (id == -1) return;

            Data input = new Data.Builder()
                    .putLong(ReminderWorker.KEY_REMINDER_ID, id)
                    .build();

            OneTimeWorkRequest w = new OneTimeWorkRequest.Builder(ReminderWorker.class)
                    .setInitialDelay(0, TimeUnit.MILLISECONDS)
                    .setInputData(input)
                    .build();

            WorkManager.getInstance(context).enqueue(w);
        } catch (Throwable t) {
            Log.e("ReminderReceiver", "err", t);
        }
    }
}
