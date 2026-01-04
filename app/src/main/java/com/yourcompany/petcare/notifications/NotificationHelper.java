package com.yourcompany.petcare.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.yourcompany.petcare.R;
import com.yourcompany.petcare.ui.reminders.ViewReminderActivity;

public final class NotificationHelper {

    private static final String CHANNEL_ID = "petcare_reminders";
    private static final String CHANNEL_NAME = "PetCare Reminders";

    private NotificationHelper(){}

    public static void ensureChannel(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            if (nm == null) return;
            NotificationChannel ch = nm.getNotificationChannel(CHANNEL_ID);
            if (ch == null) {
                ch = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                ch.setDescription("PetCare reminder channel");
                nm.createNotificationChannel(ch);
            }
        }
    }

    public static void showReminderNotification(Context ctx, long reminderId, String title, String body, String soundUri) {
        ensureChannel(ctx);

        Intent intent = new Intent(ctx, ViewReminderActivity.class);
        intent.putExtra("reminder_id", reminderId);
        PendingIntent pi = TaskStackBuilder.create(ctx)
                .addNextIntentWithParentStack(intent)
                .getPendingIntent((int)(reminderId%Integer.MAX_VALUE), PendingIntent.FLAG_UPDATE_CURRENT | pendingFlag());

        Uri sound = null;
        if (soundUri != null) sound = Uri.parse(soundUri);
        else sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder b = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setContentTitle(title == null || title.isEmpty() ? "Reminder" : title)
                .setContentText(body == null ? "" : body)
                .setSmallIcon(resolveSmallIcon(ctx))
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pi)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm != null) nm.notify((int)(reminderId%Integer.MAX_VALUE), b.build());
    }

    private static int resolveSmallIcon(Context ctx) {
        int id = ctx.getResources().getIdentifier("ic_notification", "drawable", ctx.getPackageName());
        if (id == 0) id = android.R.drawable.ic_dialog_info;
        return id;
    }

    private static int pendingFlag() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) return PendingIntent.FLAG_IMMUTABLE;
        return 0;
    }
}
