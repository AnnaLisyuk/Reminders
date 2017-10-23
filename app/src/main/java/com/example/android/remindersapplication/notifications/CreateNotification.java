package com.example.android.remindersapplication.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.icu.util.Calendar;

import com.example.android.remindersapplication.remindersItems.ReminderItems;

public class CreateNotification extends ContextWrapper {
    private String reminderID;
    private ReminderItems reminderItems;

    private AlarmManager alarmManager;
    private Intent intentAlarmReceiver;
    private PendingIntent pendingIntentAlarmReceiver;

    private Calendar calendar;

    public CreateNotification(Context base, String reminderID, String content,
                              int year, int month, int day, int hour, int minute) {
        super(base);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        this.reminderID = reminderID;

        setReminderItems(content, year, month, day, hour, minute);
        setIntentAlarmReceiver();
    }

    private void setReminderItems(String content, int year, int month, int day,
                                  int hour, int minute) {
        reminderItems = new ReminderItems();
        reminderItems.setContent(content);
        reminderItems.setYear(String.valueOf(year));
        reminderItems.setMonth(String.valueOf(month));
        reminderItems.setDay(String.valueOf(day));
        reminderItems.setHour(String.valueOf(hour));
        reminderItems.setMinute(String.valueOf(minute));
    }


    //---------- Start: SetIntentAlarmReceiver ----------//

    /**
     * Set intent for specific reminder by ID
     */
    private void setIntentAlarmReceiver() {
        intentAlarmReceiver = new Intent(this, AlarmReceiver.class);
        // Put extra content, year, month, day, hour and minute to AlarmReceiver class
        intentAlarmReceiverPutExtra();
        pendingIntentAlarmReceiver = PendingIntent.getBroadcast(this,
                Integer.parseInt(reminderID), intentAlarmReceiver,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Put extra content, year, month, day, hour and minute to AlarmReceiver class
     */
    private void intentAlarmReceiverPutExtra() {
        intentAlarmReceiver.putExtra("contentNotification", reminderItems.getContent());

        intentAlarmReceiver.putExtra("yearNotification", reminderItems.getYear());
        intentAlarmReceiver.putExtra("monthNotification", reminderItems.getMonth());
        intentAlarmReceiver.putExtra("dayNotification", reminderItems.getDay());

        intentAlarmReceiver.putExtra("hourNotification", reminderItems.getHour());
        intentAlarmReceiver.putExtra("minuteNotification", reminderItems.getMinute());
    }

    //---------- End: SetIntentAlarmReceiver ----------//


    //---------- Start: SetNotificationsChannel ----------//

    /**
     * Set notification for specific reminder by ID
     */
    public void setNotification() {
        // Set calendar for year, month, day, hour and minute
        setCalendar();
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntentAlarmReceiver);
    }

    /**
     * Set calendar for year, month, day, hour and minute
     */
    private void setCalendar() {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(reminderItems.getYear()));
        calendar.set(Calendar.MONTH, (Integer.parseInt(reminderItems.getMonth()) - 1));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(reminderItems.getDay()));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(reminderItems.getHour()));
        calendar.set(Calendar.MINUTE, Integer.parseInt(reminderItems.getMinute()));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    //---------- End: SetNotificationsChannel ----------//


    /**
     * Cancel notification for specific reminder by ID
     */
    public void cancelNotification() {
        alarmManager.cancel(pendingIntentAlarmReceiver);
    }
}
