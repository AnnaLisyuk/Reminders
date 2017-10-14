package com.example.android.remindersapplication.NotificationsSettings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.android.remindersapplication.DataBase.DataBaseSettings.FeedReaderMyDbSettings;
import com.example.android.remindersapplication.R;

import java.util.ArrayList;

class NotificationsSettingsItemAdapter extends BaseAdapter {
    private FeedReaderMyDbSettings dbSettings;

    private LayoutInflater memberInflater;
    private ArrayList<String> settingsSwitchList;
    private ArrayList<String> settingsSwitchState;

    private Switch settingsSwitch;

    private static final String SWITCH_STATE_IS_ON = "ON";
    private static final String SWITCH_STATE_IS_OFF = "OFF";

    private static final int SOUND = 0;
    private static final int VIBRATE = 1;

    private static String SETTINGS_ID;

    NotificationsSettingsItemAdapter(Context context, ArrayList<String> settingsSwitchList,
                                     ArrayList<String> settingsSwitchState, String SETTINGS_ID) {
        memberInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dbSettings = new FeedReaderMyDbSettings(context);

        // Set the settings options - Sound and Vibrate
        this.settingsSwitchList = settingsSwitchList;
        // Set the settings options state - ON or OFF
        this.settingsSwitchState = settingsSwitchState;
        // Set the settings id
        NotificationsSettingsItemAdapter.SETTINGS_ID = SETTINGS_ID;

    }

    @Override
    public int getCount() {
        return settingsSwitchList.size();
    }

    @Override
    public Object getItem(int index) {
        return settingsSwitchList.get(index);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(final int index, View view, ViewGroup viewGroup) {
        View currentView = memberInflater.inflate(R.layout.notifications_settings_details,
                null);

        settingsSwitch = currentView.findViewById(R.id.settingsSwitch);
        settingsSwitch.setText(settingsSwitchList.get(index));

        // Set switch state to ON or OFF by index from the database for display
        setSwitchStateForDisplay(index);

        // Set settings switch options on checked
        settingsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setSettingsSwitchOnChecked(index, isChecked);
            }
        });

        return currentView;
    }


    //---------- Start: SetSwitchStateForDisplay ----------//

    /**
     * Set switch state to ON or OFF by index from the database for display
     * when sound is index 0
     * and vibrate is index 1
     */
    private void setSwitchStateForDisplay(int index) {
        if (settingsSwitchState.get(index).equals(SWITCH_STATE_IS_ON))
            settingsSwitch.setChecked(true);
        else if (settingsSwitchState.get(index).equals(SWITCH_STATE_IS_OFF))
            settingsSwitch.setChecked(false);
    }

    //---------- End: SetSwitchStateForDisplay ----------//


    //---------- Start: setSettingsSwitchOnChecked ----------//

    /**
     * Set settings switch options on checked and update database
     * when sound is index 0
     * and vibrate is index 1
     */
    private void setSettingsSwitchOnChecked(int index, Boolean isChecked) {
        setSettingsSwitchState(index, isChecked);
        updateSettingsToDb();
    }

    private void setSettingsSwitchState(int index, Boolean isChecked) {
        // Get the switch state: On or Off
        if (isChecked)
            settingsSwitchState.set(index, SWITCH_STATE_IS_ON);
        else
            settingsSwitchState.set(index, SWITCH_STATE_IS_OFF);
    }

    private void updateSettingsToDb() {
        dbSettings.updateData(SETTINGS_ID, settingsSwitchState.get(SOUND),
                settingsSwitchState.get(VIBRATE));
    }

    //---------- End: setSettingsSwitchOnChecked ----------//
}
