package com.example.android.remindersapplication.createAndEditReminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.remindersapplication.R;
import com.example.android.remindersapplication.dataBase.dataBaseReminders.FeedReaderMyDbReminders;
import com.example.android.remindersapplication.dateAndTimePicker.CreateDatePicker;
import com.example.android.remindersapplication.dateAndTimePicker.CreateTimePicker;
import com.example.android.remindersapplication.mainReminders.MainRemindersActivity;
import com.example.android.remindersapplication.notifications.CreateNotification;
import com.example.android.remindersapplication.remindersItems.ReminderItems;

public class CreateReminderActivity extends AppCompatActivity {
    protected CreateDatePicker datePicker;
    protected CreateTimePicker timePicker;
    protected FeedReaderMyDbReminders dbReminders;

    protected ReminderItems reminderItems;

    protected EditText reminderEditText;
    protected Button setDateButton;
    protected Button setTimeButton;

    private static final String CREATE = "CreateReminderActivity";
    private static long REMINDER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);

        datePicker = new CreateDatePicker();
        timePicker = new CreateTimePicker();
        dbReminders = new FeedReaderMyDbReminders(this);
        reminderItems = new ReminderItems();

        setReminderEditText();
        setSetDateButton();
        setSetTimeButton();
        setSaveButton();
        setCancelButton();
    }

    protected void setReminderEditText() {
        reminderEditText = (EditText) findViewById(R.id.reminderEditText);
    }

    protected void setSetDateButton() {
        setDateButton = (Button) findViewById(R.id.setDateButton);

        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create new datePicker
                datePicker.onCreateDialog(CreateReminderActivity.this, CREATE, null, null, null);
            }
        });
    }

    protected void setSetTimeButton() {
        setTimeButton = (Button) findViewById(R.id.setTimeButton);

        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create new timePicker
                timePicker.onCreateDialog(CreateReminderActivity.this, CREATE, null, null);
            }
        });
    }


    //---------- Start: SaveButton ----------//

    protected void setSaveButton() {
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedReminderToDb()) {
                    // Create new notification
                    setNotification();
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                    setIntentMain();
                }
            }
        });
    }


    //---------- Start: SavedReminderToDb ----------//

    /**
     * On click saveButton
     */
    private boolean savedReminderToDb() {
        reminderItems.setContent(reminderEditText.getText().toString());
        setDateForReminderItems();
        setTimeForReminderItems();

        // Check if the input is valid
        if (reminderItems.checkReminderInput()) {
            // Insert date to database and get row ID
            REMINDER_ID = dbReminders.insertData(reminderItems.getContent(), reminderItems.getYear(),
                    reminderItems.getMonth(), reminderItems.getDay(), reminderItems.getHour(),
                    reminderItems.getMinute());
            return true;
        }
        Toast.makeText(this, "Some data on date or time is missing", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void setDateForReminderItems() {
        reminderItems.setYear(datePicker.getReminderYear());
        reminderItems.setMonth(datePicker.getReminderMonth());
        reminderItems.setDay(datePicker.getReminderDay());
    }

    private void setTimeForReminderItems() {
        reminderItems.setHour(timePicker.getReminderHour());
        reminderItems.setMinute(timePicker.getReminderMinute());
    }

    //---------- End: SavedReminderToDb ----------//


    /**
     * On click saveButton
     * Create new notification for reminder by ID
     */
    private void setNotification() {
        CreateNotification createNotification = new CreateNotification(getApplicationContext(),
                String.valueOf(REMINDER_ID), reminderItems.getContent(),
                Integer.parseInt(reminderItems.getYear()),
                Integer.parseInt(reminderItems.getMonth()),
                Integer.parseInt(reminderItems.getDay()),
                Integer.parseInt(reminderItems.getHour()),
                Integer.parseInt(reminderItems.getMinute()));
        createNotification.setNotification();
    }

    //---------- End: SaveButton ----------//


    protected void setCancelButton() {
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
                setIntentMain();
            }
        });
    }


    /**
     * On click saveButton and cancelButton
     */
    protected void setIntentMain() {
        Intent intentMain = new Intent(getApplicationContext(), MainRemindersActivity.class);
        startActivity(intentMain);
    }
}