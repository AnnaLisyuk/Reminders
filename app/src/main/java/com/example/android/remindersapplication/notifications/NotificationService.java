package com.example.android.remindersapplication.notifications;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.android.remindersapplication.dataBase.dataBaseSettings.FeedReaderMyDbSettings;
import com.example.android.remindersapplication.remindersItems.ReminderItems;

import java.util.ArrayList;

public class NotificationService extends Service {
    private FeedReaderMyDbSettings dbSettings;
    private ArrayList<String> settingsSwitchState;
    private ReminderItems reminderItems;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        dbSettings = new FeedReaderMyDbSettings(this);
        settingsSwitchState = new ArrayList<>();

        // Receive settings switch state from a database
        receiveSettingsSwitchStateFromDb();
        // Get extra content, date and time from CreateNotification class
        getExtraReminderDetails(intent);
        // Set notification channel and builder
        setNotification();

        return START_NOT_STICKY;
    }


    //---------- Start: ReceiveSettingsSwitchStateFromDb ----------//

    /**
     * Receive settings switch state from the database
     * and set settingsSwitchState
     */
    private void receiveSettingsSwitchStateFromDb() {
        Cursor cursor = dbSettings.readData();
        if (cursor.getCount() == 0)
            return;
        while (cursor.moveToNext()) {
            setSettingsSwitchState(cursor);
        }
    }

    private void setSettingsSwitchState(Cursor cursor) {
        // Add sound state
        settingsSwitchState.add(cursor.getString(1));
        // Add vibrate state
        settingsSwitchState.add(cursor.getString(2));
    }

    //---------- End: ReceiveSettingsSwitchStateFromDb ----------//


    private void getExtraReminderDetails(Intent intent) {
        reminderItems = new ReminderItems();

        reminderItems.setContent(intent.getStringExtra("contentAlarm"));

        reminderItems.setYear(intent.getStringExtra("yearAlarm"));
        reminderItems.setMonth(intent.getStringExtra("monthAlarm"));
        reminderItems.setDay(intent.getStringExtra("dayAlarm"));

        reminderItems.setHour(intent.getStringExtra("hourAlarm"));
        reminderItems.setMinute(intent.getStringExtra("minuteAlarm"));
    }


    //---------- Start: SetNotification ----------//

    /**
     * Set notification channel and builder for android version
     */
    private void setNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            notificationsChannel();
        else
            notificationsBuilder();
    }

    private void notificationsChannel() {
        SetNotificationsChannel setNotificationsChannel = new SetNotificationsChannel(this,
                settingsSwitchState, reminderItems);
        setNotificationsChannel.setNotificationChannel();
        setNotificationsChannel.setNotificationBuilderChannel();
    }

    private void notificationsBuilder() {
        SetNotificationsBuilder setNotificationsBuilder = new SetNotificationsBuilder(this,
                settingsSwitchState, reminderItems);
        setNotificationsBuilder.setNotificationBuilder();
    }

    //---------- End: SetNotification ----------//
}

