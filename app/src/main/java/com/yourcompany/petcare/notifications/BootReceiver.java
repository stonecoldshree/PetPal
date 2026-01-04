package com.yourcompany.petcare.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            OneTimeWorkRequest w = new OneTimeWorkRequest.Builder(InitWorker.class).build();
            WorkManager.getInstance(context).enqueue(w);
        } catch (Throwable t) {
            Log.e("BootReceiver", "Exception in onReceive", t);
        }
    }
}
