package com.example.android.remindersapplication.Notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import com.example.android.remindersapplication.MainReminders.MainRemindersActivity;
import com.example.android.remindersapplication.R;
import com.example.android.remindersapplication.RemindersItems.ReminderItems;
import com.example.android.remindersapplication.RemindersItems.RemindersItemsList;

import java.util.ArrayList;

public abstract class SetNotifications extends ContextWrapper {
    protected NotificationManager mNotificationManager;
    protected PendingIntent pendingIntentMainReminders;

    protected ArrayList<String> settingsSwitchState;
    protected ReminderItems reminderItems;

    protected static final String SWITCH_STATE_IS_ON = "ON";
    protected static final int SOUND = 0;
    protected static final int VIBRATE = 1;

    public SetNotifications(Context base) {
        super(base);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    protected void setStateAndItems(ArrayList<String> settingsSwitchState,
                                    ReminderItems reminderItems) {
        this.settingsSwitchState = settingsSwitchState;
        this.reminderItems = reminderItems;
    }


    //---------- Start: SetNotificationBuilderDetails ----------//

    protected abstract void setNotificationBuilderDetails();

    /**
     * The title is the reminder content
     */
    protected String getTitle() {
        return reminderItems.getContent();
    }


    //---------- Start: GetContent ----------//

    /**
     * The content is the combination of reminder date and time
     */
    protected String getContent() {
        // Combine date and time to string format and get Content
        RemindersItemsList remindersItemsList = new RemindersItemsList();
        return remindersItemsList.combineStringDateAndTime(getDateFormat(remindersItemsList),
                getTimeFormat(remindersItemsList));
    }

    /**
     * Combine date for year, month and day
     */
    private String getDateFormat(RemindersItemsList remindersItemsList) {
        return remindersItemsList.combineStringDate(reminderItems.getYear(),
                reminderItems.getMonth(), reminderItems.getDay());
    }

    /**
     * Combine time for hour and minute
     */
    private String getTimeFormat(RemindersItemsList remindersItemsList) {
        return remindersItemsList.combineStringTime(checkTimeFormat(reminderItems.getHour()),
                checkTimeFormat(reminderItems.getMinute()));
    }

    private String checkTimeFormat(String number) {
        // If time number smaller than 10 so add zero before the number
        if (Integer.parseInt(number) < 10)
            return "0" + number;
        return number;
    }

    //---------- End: GetContent ----------//


    protected int getSmallIcon() {
        return R.drawable.ic_notifications_black_24dp;
    }


    //---------- End: SetNotificationBuilderDetails ----------//


    /**
     * Set notification click behavior
     */
    protected void setIntentMainReminders() {
        Intent intentMainReminders = new Intent(this, MainRemindersActivity.class);
        pendingIntentMainReminders = PendingIntent.getActivity(this, 0,
                intentMainReminders, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    //---------- Start: SetSoundAndVibration ----------//

    protected abstract void setSound();

    /**
     * If sound state is ON
     */
    protected boolean isSoundStateIsON() {
        return settingsSwitchState.get(SOUND).equals(SWITCH_STATE_IS_ON);
    }

    protected abstract void setVibration();

    /**
     * If vibrate state is ON
     */
    protected boolean isVibrateStateIsON() {
        return settingsSwitchState.get(VIBRATE).equals(SWITCH_STATE_IS_ON);
    }

    //---------- End: SetSoundAndVibration ----------//
}
