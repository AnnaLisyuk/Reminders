package com.example.android.remindersapplication.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;

import com.example.android.remindersapplication.RemindersItems.ReminderItems;

import java.util.ArrayList;

public class SetNotificationsChannel extends SetNotifications {
    private static final String CHANNEL_ID = "remindersapplication.Notifications.ID";
    private static final String CHANNEL_NAME = "remindersapplication.Notifications.NAME";

    private Notification.Builder mBuilderChannel;
    private NotificationChannel mChannel;

    public SetNotificationsChannel(Context base, ArrayList<String> settingsSwitchState,
                                   ReminderItems reminderItems) {
        super(base);
        // Set settingsSwitchState and reminderItems
        setStateAndItems(settingsSwitchState, reminderItems);
    }


    //---------- Start: SetNotificationChannel ----------//

    public void setNotificationChannel() {
        mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH);
        setSound();
        setVibration();
        mNotificationManager.createNotificationChannel(mChannel);
    }

    //---------- Start: SetSoundAndVibration ----------//

    @Override
    protected void setSound() {
        if (isSoundStateIsON()) {
            mChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                    null);
        }
    }

    @Override
    protected void setVibration() {
        if (isVibrateStateIsON())
            mChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
    }

    //---------- End: SetSoundAndVibration ----------//

    //---------- End: SetNotificationChannel ----------//


    //---------- Start: SetNotificationBuilderChannel ----------//

    public void setNotificationBuilderChannel() {
        mBuilderChannel = new Notification.Builder(this, CHANNEL_ID);
        setNotificationBuilderDetails();
        mNotificationManager.notify(0, mBuilderChannel.build());
    }


    //---------- Start: SetNotificationBuilderDetails ----------//

    @Override
    protected void setNotificationBuilderDetails() {
        mBuilderChannel.setContentTitle(getTitle());
        mBuilderChannel.setContentText(getContent());
        mBuilderChannel.setSmallIcon(getSmallIcon());

        // Set notification click behavior
        setIntentMainReminders();
    }

    /**
     * Set notification click behavior
     */
    @Override
    protected void setIntentMainReminders() {
        super.setIntentMainReminders();
        mBuilderChannel.setContentIntent(pendingIntentMainReminders);
    }

    //---------- End: SetNotificationBuilderDetails ----------//

    //---------- End: SetNotificationBuilderChannel ----------//
}
