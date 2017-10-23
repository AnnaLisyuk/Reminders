package com.example.android.remindersapplication.dateAndTimePicker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.android.remindersapplication.R;

import java.util.Calendar;

public class CreateDatePicker {
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private String reminderYear;
    private String reminderMonth;
    private String reminderDay;

    private static final String CREATE = "CreateReminderActivity";
    private static final String EDIT = "EditReminderActivity";

    public CreateDatePicker() {
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                reminderYear = year + "";
                reminderMonth = (month + 1) + "";
                reminderDay = day + "";

                Toast.makeText(view.getContext(), setTextForDisplay(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * Set text for date display
     */
    private String setTextForDisplay() {
        return reminderDay + "/" + reminderMonth + "/" + reminderYear;
    }


    //---------- Start: OnCreateDialog ----------//

    public void onCreateDialog(Context context, String reminderActivity,
                               String year, String month, String day) {
        // Create new datePicker
        if (reminderActivity.equals(CREATE))
            onCreateDialogCreateReminderActivity(context);
            // Set exists date for datePicker
        else if (reminderActivity.equals(EDIT))
            onCreateDialogEditReminderActivity(context, year, month, day);
    }

    /**
     * Display date on create for CreateReminderActivity
     */
    private void onCreateDialogCreateReminderActivity(Context context) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                R.style.DialogTheme, dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * Display date on create for EditReminderActivity
     */
    private void onCreateDialogEditReminderActivity(Context context,
                                                    String year, String month, String day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                R.style.DialogTheme, dateSetListener,
                Integer.parseInt(year), (Integer.parseInt(month) - 1), Integer.parseInt(day));
        datePickerDialog.show();
    }

    //---------- End: OnCreateDialog ----------//


    //---------- Start: GetReminderDate ----------//

    public String getReminderYear() {
        return reminderYear;
    }

    public String getReminderMonth() {
        return reminderMonth;
    }

    public String getReminderDay() {
        return reminderDay;
    }

    //---------- End: GetReminderDate ----------//
}
