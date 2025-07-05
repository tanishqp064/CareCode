package com.example.carecodeee;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.*;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String medName = intent.getStringExtra("medName");

        // Create NotificationManager instance
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification channel ID and name
        String channelId = "med_channel";

        // Create the notification channel (needed for Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Set the importance for the notification channel
            NotificationChannel channel = new NotificationChannel(channelId, "Medicine Reminder", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel for medicine reminders");
            notificationManager.createNotificationChannel(channel);
        }

        // Intent for opening DashboardActivity when the user clicks the notification
        Intent repeatingIntent = new Intent(context, DashboardActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        // PendingIntent to wrap the repeatingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.asus)  // Replace with your custom icon
                .setContentTitle("Time to take your medicine")
                .setContentText(medName)
                .setPriority(NotificationCompat.PRIORITY_HIGH)  // Set high priority to make sure it shows on time
                .setAutoCancel(true);  // Dismiss notification when clicked

        // Trigger the notification
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
