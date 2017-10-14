package com.example.android.remindersapplication.NotificationsSettings;

import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.remindersapplication.DataBase.DataBaseSettings.FeedReaderMyDbSettings;
import com.example.android.remindersapplication.R;

import java.util.ArrayList;
import java.util.Arrays;

public class NotificationsSettingsActivity extends AppCompatActivity {
    private FeedReaderMyDbSettings dbSettings;

    private ListView settingsListView;
    private String[] settingsSwitch;
    private ArrayList<String> settingsSwitchState;

    private static final String SWITCH_STATE_IS_ON = "ON";
    private static String SETTINGS_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_settings);

        dbSettings = new FeedReaderMyDbSettings(this);
        settingsSwitchState = new ArrayList<>();

        saveDefaultSettingsToDb();
        receiveSettingsFromDb();

        setSettingsListView();
    }

    /**
     * Save default settings to database if the database is empty
     * Sound is ON and Vibrate is On
     */
    private void saveDefaultSettingsToDb() {
        Cursor cursor = dbSettings.readData();
        if (cursor.getCount() == 0) {
            dbSettings.insertData(SWITCH_STATE_IS_ON, SWITCH_STATE_IS_ON);
        }
    }


    //---------- Start: ReceiveSettingsFromDb ----------//

    /**
     * Receive settings from a database
     */
    private void receiveSettingsFromDb() {
        Cursor cursor = dbSettings.readData();
        while (cursor.moveToNext()) {
            addItemsToSettingsStateSwitch(cursor);
        }
    }

    /**
     * Set settings id
     * and add items to settingsSwitchState from database
     */
    private void addItemsToSettingsStateSwitch(Cursor cursor) {
        // Set id
        SETTINGS_ID = cursor.getString(0);
        // Add sound state
        settingsSwitchState.add(cursor.getString(1));
        // Add vibrate state
        settingsSwitchState.add(cursor.getString(2));
    }

    //---------- End: ReceiveSettingsFromDb ----------//


    //---------- Start: SetSettingsListView ----------//

    private void setSettingsListView() {
        settingsListView = (ListView) findViewById(R.id.settingsListView);

        setResources();
        activateSettingsItemAdapter();
    }

    /**
     * Set resources for settings options
     * get settings switch items: Sound and Vibrate
     */
    private void setResources() {
        Resources resources = getResources();
        settingsSwitch = resources.getStringArray(R.array.settings_switch);
    }

    /**
     * Activate the NotificationsSettingsItemAdapter class
     */
    private void activateSettingsItemAdapter() {
        // Sent settingsSwitch for settings options - Sound and Vibrate
        // Sent settingsSwitchState for settings options state - ON or OFF
        // Sent settings id
        NotificationsSettingsItemAdapter notificationsSettingsItemAdapter =
                new NotificationsSettingsItemAdapter(this,
                        new ArrayList<>(Arrays.asList(settingsSwitch)),
                        settingsSwitchState, SETTINGS_ID);
        settingsListView.setAdapter(notificationsSettingsItemAdapter);
    }

    //---------- End: SetSettingsListView ----------//
}
