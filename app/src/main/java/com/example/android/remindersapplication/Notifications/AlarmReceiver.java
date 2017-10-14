package com.example.android.remindersapplication.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.android.remindersapplication.RemindersItems.ReminderItems;

public class AlarmReceiver extends BroadcastReceiver {
    private Intent intentNotificationService;
    private ReminderItems reminderItems;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get extra content, date and time from CreateNotification class
        getExtraReminderDetails(intent);
        setIntentLocalService(context);
    }

    /**
     * Get extra content, year, month, day, hour and minute to NotificationService class
     */
    private void getExtraReminderDetails(Intent intent) {
        reminderItems = new ReminderItems();

        reminderItems.setContent(intent.getStringExtra("contentNotification"));

        reminderItems.setYear(intent.getStringExtra("yearNotification"));
        reminderItems.setMonth(intent.getStringExtra("monthNotification"));
        reminderItems.setDay(intent.getStringExtra("dayNotification"));

        reminderItems.setHour(intent.getStringExtra("hourNotification"));
        reminderItems.setMinute(intent.getStringExtra("minuteNotification"));
    }


    //---------- Start: SetIntentLocalService ----------//

    private void setIntentLocalService(Context context) {
        intentNotificationService = new Intent(context, NotificationService.class);
        // Put extra content, year, month, day, hour and minute to NotificationService class
        intentNotificationServicePutExtra();
        context.startService(intentNotificationService);
    }

    /**
     * Put extra content, year, month, day, hour and minute to NotificationService class
     */
    private void intentNotificationServicePutExtra() {
        intentNotificationService.putExtra("contentAlarm", reminderItems.getContent());

        intentNotificationService.putExtra("yearAlarm", String.valueOf(reminderItems.getYear()));
        intentNotificationService.putExtra("monthAlarm", String.valueOf(reminderItems.getMonth()));
        intentNotificationService.putExtra("dayAlarm", String.valueOf(reminderItems.getDay()));

        intentNotificationService.putExtra("hourAlarm", String.valueOf(reminderItems.getHour()));
        intentNotificationService.putExtra("minuteAlarm", String.valueOf(reminderItems.getMinute()));
    }

    //---------- End: SetIntentLocalService ----------//
}
