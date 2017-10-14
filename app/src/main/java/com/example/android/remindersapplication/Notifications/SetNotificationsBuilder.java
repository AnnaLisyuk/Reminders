package com.example.android.remindersapplication.Notifications;

import android.content.Context;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.example.android.remindersapplication.RemindersItems.ReminderItems;

import java.util.ArrayList;

public class SetNotificationsBuilder extends SetNotifications {
    private NotificationCompat.Builder mBuilder;

    public SetNotificationsBuilder(Context base, ArrayList<String> settingsSwitchState,
                                   ReminderItems reminderItems) {
        super(base);
        // Set settingsSwitchState and reminderItems
        setStateAndItems(settingsSwitchState, reminderItems);
    }


    //---------- Start: SetNotificationBuilder ----------//

    public void setNotificationBuilder() {
        mBuilder = new NotificationCompat.Builder(this);
        setNotificationBuilderDetails();
        mNotificationManager.notify(0, mBuilder.build());
    }


    //---------- Start: SetNotificationBuilderDetails ----------//

    @Override
    protected void setNotificationBuilderDetails() {
        mBuilder.setContentTitle(getTitle());
        mBuilder.setContentText(getContent());
        mBuilder.setSmallIcon(getSmallIcon());

        setSound();
        setVibration();
        // Set notification click behavior
        setIntentMainReminders();
    }


    //---------- Start: SetSoundAndVibration ----------//

    @Override
    protected void setSound() {
        if (isSoundStateIsON())
            mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
    }

    @Override
    protected void setVibration() {
        if (isVibrateStateIsON())
            mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
    }

    //---------- End: SetSoundAndVibration ----------//


    /**
     * Set notification click behavior
     */
    @Override
    protected void setIntentMainReminders() {
        super.setIntentMainReminders();
        mBuilder.setContentIntent(pendingIntentMainReminders);
    }

    //---------- End: SetNotificationBuilderDetails ----------//

    //---------- End: SetNotificationBuilder ----------//
}
