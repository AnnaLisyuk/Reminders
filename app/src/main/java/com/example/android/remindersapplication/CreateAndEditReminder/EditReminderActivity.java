package com.example.android.remindersapplication.CreateAndEditReminder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.remindersapplication.DataBase.DataBaseReminders.FeedReaderMyDbReminders;
import com.example.android.remindersapplication.DateAndTimePicker.CreateDatePicker;
import com.example.android.remindersapplication.DateAndTimePicker.CreateTimePicker;
import com.example.android.remindersapplication.Notifications.CreateNotification;
import com.example.android.remindersapplication.R;
import com.example.android.remindersapplication.RemindersItems.ReminderItems;

public class EditReminderActivity extends CreateReminderActivity {
    private String reminderId;

    private boolean pressDate = false;
    private boolean pressTime = false;

    private static final String EDIT = "EditReminderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);

        datePicker = new CreateDatePicker();
        timePicker = new CreateTimePicker();
        dbReminders = new FeedReaderMyDbReminders(this);

        // Get extra id from MainReminderItemAdapter
        reminderId = getIntent().getStringExtra("reminderId");

        // Get extra content, date and time from MainReminderItemAdapter class
        getExtraReminderDetails();

        setReminderEditText();
        setSetDateButton();
        setSetTimeButton();
        setSaveButton();
        setCancelButton();
    }

    /**
     * Get extra content, date and time from MainReminderItemAdapter class
     */
    private void getExtraReminderDetails() {
        reminderItems = new ReminderItems();
        reminderItems.setContent(getIntent().getStringExtra("reminderContent"));

        reminderItems.setYear(getIntent().getStringExtra("reminderYear"));
        reminderItems.setMonth(getIntent().getStringExtra("reminderMonth"));
        reminderItems.setDay(getIntent().getStringExtra("reminderDay"));

        reminderItems.setHour(getIntent().getStringExtra("reminderHour"));
        reminderItems.setMinute(getIntent().getStringExtra("reminderMinute"));
    }

    @Override
    protected void setReminderEditText() {
        reminderEditText = (EditText) findViewById(R.id.reminderEditText);
        reminderEditText.setText(reminderItems.getContent());
    }

    @Override
    protected void setSetDateButton() {
        setDateButton = (Button) findViewById(R.id.setDateButton);
        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get exists date for datePicker
                datePicker.onCreateDialog(EditReminderActivity.this, EDIT,
                        reminderItems.getYear(), reminderItems.getMonth(), reminderItems.getDay());
                // If user press on setDateButton
                pressDate = true;
            }
        });
    }

    @Override
    protected void setSetTimeButton() {
        setTimeButton = (Button) findViewById(R.id.setTimeButton);
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get exists date for timePicker
                timePicker.onCreateDialog(EditReminderActivity.this, EDIT,
                        reminderItems.getHour(), reminderItems.getMinute());
                // If user press on setTimeButton
                pressTime = true;
            }
        });
    }


    //---------- Start: SaveButton ----------//

    @Override
    protected void setSaveButton() {
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateReminderToDb()) {
                    // Update notification
                    updateNotification();

                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                    setIntentMain();
                }
            }
        });
    }


    //---------- Start: UpdateReminderToDb ----------//

    /**
     * On click saveButton
     */
    private boolean updateReminderToDb() {
        reminderItems.setContent(reminderEditText.getText().toString());
        setDateForReminderItems();
        setTimeForReminderItems();

        // Check if the input is valid
        if (reminderItems.checkReminderInput()) {
            dbReminders.updateData(reminderId, reminderItems.getContent(), reminderItems.getYear(),
                    reminderItems.getMonth(), reminderItems.getDay(), reminderItems.getHour(),
                    reminderItems.getMinute());
            return true;
        }
        Toast.makeText(this, "Some data on date or time is missing", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void setDateForReminderItems() {
        // If user press on setDateButton then take date from datePicker
        if (pressDate) {
            reminderItems.setYear(datePicker.getReminderYear());
            reminderItems.setMonth(datePicker.getReminderMonth());
            reminderItems.setDay(datePicker.getReminderDay());
        }
    }

    private void setTimeForReminderItems() {
        // If user press on setTimeButton then take time from timePicker
        if (pressTime) {
            reminderItems.setHour(timePicker.getReminderHour());
            reminderItems.setMinute(timePicker.getReminderMinute());
        }
    }

    //---------- End: UpdateReminderToDb ----------//


    /**
     * On click saveButton
     * Update notification for reminder by ID
     */
    private void updateNotification() {
        CreateNotification createNotification = new CreateNotification(
                getApplicationContext(), reminderId,
                reminderItems.getContent(),
                Integer.parseInt(reminderItems.getYear()),
                Integer.parseInt(reminderItems.getMonth()),
                Integer.parseInt(reminderItems.getDay()),
                Integer.parseInt(reminderItems.getHour()),
                Integer.parseInt(reminderItems.getMinute()));
        // First cancel previous notification
        createNotification.cancelNotification();
        // Then create new notification
        createNotification.setNotification();
    }

    //---------- End: SaveButton ----------//
}

