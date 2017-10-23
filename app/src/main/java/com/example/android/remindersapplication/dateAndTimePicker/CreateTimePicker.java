package com.example.android.remindersapplication.dateAndTimePicker;

import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.remindersapplication.R;

import java.util.Calendar;

public class CreateTimePicker {
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private String reminderHour;
    private String reminderMinute;

    private static final String CREATE = "CreateReminderActivity";
    private static final String EDIT = "EditReminderActivity";

    public CreateTimePicker() {
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                reminderHour = checkTimeFormat(hourOfDay);
                reminderMinute = checkTimeFormat(minute);

                Toast.makeText(view.getContext(), setTextForDisplay(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * Check time format
     */
    private String checkTimeFormat(int time) {
        // If time number smaller than 10 so add zero before the number
        if (time < 10)
            return "0" + time;
        return time + "";
    }

    /**
     * Set text for time display
     */
    private String setTextForDisplay() {
        return reminderHour + ":" + reminderMinute;
    }


    //---------- Start: OnCreateDialog ----------//

    public void onCreateDialog(Context context, String reminderActivity,
                               String hour, String minute) {
        // Create new timePicker
        if (reminderActivity.equals(CREATE))
            onCreateDialogCreateReminderActivity(context);
            // Set exists time for timePicker
        else if (reminderActivity.equals(EDIT))
            onCreateDialogEditReminderActivity(context, hour, minute);
    }

    /**
     * Display date on create for CreateReminderActivity
     */
    private void onCreateDialogCreateReminderActivity(Context context) {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                R.style.DialogTheme, timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(context));
        timePickerDialog.show();
    }

    /**
     * Display date on create for EditReminderActivity
     */
    private void onCreateDialogEditReminderActivity(Context context, String hour, String minute) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                R.style.DialogTheme, timeSetListener,
                Integer.parseInt(hour), Integer.parseInt(minute),
                DateFormat.is24HourFormat(context));
        timePickerDialog.show();
    }

    //---------- End: OnCreateDialog ----------//


    //---------- Start: GetReminderTime ----------//

    public String getReminderHour() {
        return reminderHour;
    }

    public String getReminderMinute() {
        return reminderMinute;
    }

    //---------- End: GetReminderTime ----------//
}

