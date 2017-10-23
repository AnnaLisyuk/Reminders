package com.example.android.remindersapplication.mainReminders;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.remindersapplication.R;
import com.example.android.remindersapplication.createAndEditReminder.EditReminderActivity;
import com.example.android.remindersapplication.dataBase.dataBaseReminders.FeedReaderMyDbReminders;
import com.example.android.remindersapplication.notifications.CreateNotification;
import com.example.android.remindersapplication.remindersItems.ReminderItems;
import com.example.android.remindersapplication.remindersItems.RemindersItemsList;

import java.util.ArrayList;

class MainRemindersItemAdapter extends BaseAdapter {
    private LayoutInflater memberInflater;
    private FeedReaderMyDbReminders dbReminders;

    private RemindersItemsList remindersItemsList;
    private ReminderItems reminderItems;

    MainRemindersItemAdapter(Context context, RemindersItemsList remindersItemsList) {
        memberInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dbReminders = new FeedReaderMyDbReminders(context);

        this.remindersItemsList = remindersItemsList;
        // Create reminderItems for split date and time to day, month, year and hour, minute
        reminderItems = new ReminderItems();
    }

    @Override
    public int getCount() {
        return remindersItemsList.getRemindersId().size();
    }

    @Override
    public Object getItem(int index) {
        return remindersItemsList.getRemindersId().get(index);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(final int index, View view, ViewGroup viewGroup) {
        View currentView = memberInflater.inflate(R.layout.main_reminders_listview_details, null);

        TextView reminderIdTextView = currentView.findViewById(R.id.reminderIdTextView);
        TextView reminderContentTextView = currentView.findViewById(R.id.reminderContentTextView);
        TextView reminderDateAndTimeTextView =
                currentView.findViewById(R.id.reminderDateAndTimeTextView);

        final Button reminderButton = currentView.findViewById(R.id.reminderButton);

        setReminderTextView(index, reminderIdTextView, reminderContentTextView,
                reminderDateAndTimeTextView);
        setReminderButton(index, reminderButton);
        return currentView;
    }

    /**
     * Set all reminder textView
     */
    private void setReminderTextView(int index, TextView reminderIdTextView,
                                     TextView reminderContentTextView,
                                     TextView reminderDateAndTimeTextView) {
        reminderIdTextView.setText(remindersItemsList.getRemindersId().get(index));
        reminderContentTextView.setText(remindersItemsList.getRemindersContent().get(index));
        reminderDateAndTimeTextView.setText(
                remindersItemsList.getRemindersDateAndTime().get(index));
    }


    //---------- Start: ReminderButton ----------//

    /**
     * Set reminder button
     */
    private void setReminderButton(final int index, final Button reminderButton) {
        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                setReminderItemPopupMenu(view, index);
            }
        });
    }


    //---------- Start: ReminderItemPopupMenu ----------//

    /**
     * Set PopupMenu form item
     */
    private void setReminderItemPopupMenu(final View view, final int index) {
        PopupMenu reminderItemPopupMenu = new PopupMenu(view.getContext(), view);
        reminderItemPopupMenu.inflate(R.menu.reminders_listview_item);

        reminderItemPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return setReminderListViewItemMenu(item, view, index);
            }
        });
        reminderItemPopupMenu.show();
    }


    //---------- Start: SetReminderListViewItemMenu ----------//

    /**
     * Set menu options
     */
    private boolean setReminderListViewItemMenu(MenuItem item, final View view, int index) {
        int itemId = item.getItemId();

        if (itemId == R.id.reminderListViewItemOptionEdit) {
            setIntentEditReminder(view.getContext(), index);
        } else if (itemId == R.id.reminderListViewItemOptionDelete) {
            deleteReminder(view, index);
        }
        return true;
    }


    //---------- Start: EditReminder ----------//

    /**
     * On click menu option Edit
     */
    private void setIntentEditReminder(Context context, int index) {
        Intent intentEditReminder = new Intent(context, EditReminderActivity.class);
        // Put extra id, content, date and time to EditReminderActivity class
        putExtraAllData(intentEditReminder, index);
        context.startActivity(intentEditReminder);
    }

    /**
     * Put extra all reminder data to EditReminder class
     */
    private void putExtraAllData(Intent intentEditReminder, int index) {
        // Put extra id
        intentEditReminder.putExtra("reminderId", remindersItemsList.getRemindersId().get(index));
        // Put extra content
        intentEditReminder.putExtra("reminderContent",
                remindersItemsList.getRemindersContent().get(index));
        // Put extra Date and Time
        putExtraDateAndTime(intentEditReminder, index);
    }

    /**
     * Put extra Date and Time to EditReminder class
     */
    private void putExtraDateAndTime(Intent intentEditReminder, int index) {
        // Split date and time string to day, month, year and hour, minute
        reminderItems.splitDateAndTime(remindersItemsList.getRemindersDateAndTime().get(index));

        intentEditReminder.putExtra("reminderYear", reminderItems.getYear());
        intentEditReminder.putExtra("reminderMonth", reminderItems.getMonth());
        intentEditReminder.putExtra("reminderDay", reminderItems.getDay());

        intentEditReminder.putExtra("reminderHour", reminderItems.getHour());
        intentEditReminder.putExtra("reminderMinute", reminderItems.getMinute());
    }

    //---------- End: EditReminder ----------//


    //---------- Start: DeleteReminder ----------//

    /**
     * Delete reminder from the database on click menu option Delete
     * and cancel reminder notification
     */
    private void deleteReminder(View view, int index) {
        // Delete reminder with current index
        if (dbReminders.deleteData(remindersItemsList.getRemindersId().get(index))) {
            // Cancel reminder notification with current index
            cancelNotification(view.getContext(), index);

            setIntentMainReminder(view.getContext());
            Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
        }
    }


    //---------- Start: CancelNotifications ----------//

    /**
     * On click menu option Delete
     * Cancel reminder notification by ID
     */
    private void cancelNotification(Context context, int index) {
        ArrayList<String> reminderContent = remindersItemsList.getRemindersContent();

        ArrayList<String> reminderYear = remindersItemsList.getRemindersYear();
        ArrayList<String> reminderMonth = remindersItemsList.getRemindersMonth();
        ArrayList<String> reminderDay = remindersItemsList.getRemindersDay();

        ArrayList<String> reminderHour = remindersItemsList.getRemindersHour();
        ArrayList<String> reminderMinute = remindersItemsList.getRemindersMinute();

        setCreateNotification(context, index, reminderContent, reminderYear, reminderMonth,
                reminderDay, reminderHour, reminderMinute);
    }

    private void setCreateNotification(Context context, int index,
                                       ArrayList<String> reminderContent,
                                       ArrayList<String> reminderYear,
                                       ArrayList<String> reminderMonth,
                                       ArrayList<String> reminderDay,
                                       ArrayList<String> reminderHour,
                                       ArrayList<String> reminderMinute) {
        CreateNotification createNotification = new CreateNotification(
                context, remindersItemsList.getRemindersId().get(index), reminderContent.get(index),
                Integer.parseInt(reminderYear.get(index)),
                Integer.parseInt(reminderMonth.get(index)),
                Integer.parseInt(reminderDay.get(index)),
                Integer.parseInt(reminderHour.get(index)),
                Integer.parseInt(reminderMinute.get(index)));
        createNotification.cancelNotification();
    }

    //---------- End: CancelNotifications ----------//


    /**
     * On click menu option Delete
     */
    private void setIntentMainReminder(Context context) {
        Intent intentMainReminder = new Intent(context, MainRemindersActivity.class);
        context.startActivity(intentMainReminder);
    }

    //---------- End: DeleteReminder ----------//

    //---------- End: SetReminderListViewItemMenu ----------//

    //---------- End: ReminderItemPopupMenu ----------//

    //---------- End: ReminderButton ----------//
}
