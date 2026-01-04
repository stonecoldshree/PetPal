package com.yourcompany.petcare.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yourcompany.petcare.utils.NotificationHelper;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra("reminderId", -1);
        String title = intent.getStringExtra("title");
        String notes = intent.getStringExtra("notes");

        NotificationHelper nh = new NotificationHelper(context);
        nh.showNotification((int) id, title != null ? title : "Reminder", notes != null ? notes : "");
    }
}
