package com.example.android.remindersapplication.MainReminders;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.remindersapplication.CreateAndEditReminder.CreateReminderActivity;
import com.example.android.remindersapplication.DataBase.DataBaseReminders.FeedReaderMyDbReminders;
import com.example.android.remindersapplication.Notifications.CreateNotification;
import com.example.android.remindersapplication.NotificationsSettings.NotificationsSettingsActivity;
import com.example.android.remindersapplication.R;
import com.example.android.remindersapplication.RemindersItems.RemindersItemsList;
import com.example.android.remindersapplication.RemindersItems.SortRemindersItemsList;

import java.util.ArrayList;

public class MainRemindersActivity extends AppCompatActivity {
    private FeedReaderMyDbReminders dbReminders;

    private RemindersItemsList remindersItemsList;
    private MainRemindersItemAdapter mainRemindersItemAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private ListView remindersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reminders);

        // For creating the list
        dbReminders = new FeedReaderMyDbReminders(this);
        remindersItemsList = new RemindersItemsList();

        receiveRemindersListFromDb();
        setRemindersListView();

        setBottomNavigationView();
    }


    //---------- Start: ReceiveRemindersListFromDb ----------//

    /**
     * Receive reminders from a database
     */
    private void receiveRemindersListFromDb() {
        Cursor cursor = dbReminders.readData();
        if (cursor.getCount() == 0)
            return;
        while (cursor.moveToNext()) {
            addItemsToRemindersItemsList(cursor);
        }
    }


    //---------- Start: AddItemsToRemindersItemsList ----------//

    /**
     * Add items to reminders list from database
     */
    private void addItemsToRemindersItemsList(Cursor cursor) {
        remindersItemsList.addId(cursor.getString(0));
        remindersItemsList.addContent(cursor.getString(1));
        // Add year, month and day
        remindersItemsList.addDate(cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // Add hour and minute
        remindersItemsList.addTime(cursor.getString(5), cursor.getString(6));

        // After add new reminder, sort the remindersItemsList
        sortRemindersItemsList();
    }

    /**
     * Sort remindersItemsList
     */
    private void sortRemindersItemsList() {
        // SortRemindersItemsList get the current remindersItemsList
        // then sort the list, and return sorted list
        SortRemindersItemsList sortRemindersItemsList =
                new SortRemindersItemsList(remindersItemsList);
        remindersItemsList = sortRemindersItemsList.getSortedRemindersItemsList();
    }

    //---------- End: AddItemsToRemindersItemsList ----------//

    //---------- End: ReceiveRemindersListFromDb ----------//


    //---------- Start: SetRemindersListView ----------//

    private void setRemindersListView() {
        remindersListView = (ListView) findViewById(R.id.remindersListView);
        activateMainReminderItemAdapter();
    }

    /**
     * Activate the MainRemindersItemAdapter class
     */
    private void activateMainReminderItemAdapter() {
        mainRemindersItemAdapter = new MainRemindersItemAdapter(this, remindersItemsList);
        remindersListView.setAdapter(mainRemindersItemAdapter);
    }

    //---------- End: SetRemindersListView ----------//


    //---------- Start: SetBottomNavigationView ----------//

    private void setBottomNavigationView() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        setMOnNavigationItemSelectedListener();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setMOnNavigationItemSelectedListener() {
        mOnNavigationItemSelectedListener = new
                BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        return setNavigationMenu(item);
                    }
                };
    }

    private boolean setNavigationMenu(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_create:
                setIntentCreateReminder();
                return true;
            case R.id.navigation_delete:
                deleteAllReminders();
                return true;
            case R.id.navigation_notifications:
                setIntentNotificationsSettings();
                return true;
        }
        return false;
    }

    /**
     * On click menu option Create
     */
    private void setIntentCreateReminder() {
        Intent intentCreateReminder = new Intent(getApplicationContext(),
                CreateReminderActivity.class);
        startActivity(intentCreateReminder);
    }


    //---------- Start: DeleteAll ----------//

    /**
     * On click menu option Delete all
     * Delete all reminders from the database
     * and cancel all notifications
     */
    private void deleteAllReminders() {
        Cursor cursor = dbReminders.readData();
        // If the database is empty
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "The list is empty", Toast.LENGTH_SHORT).show();
        } else if (dbReminders.deleteAllData()) {
            cancelAllNotifications();
            setIntentMainReminders();
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        }
    }


    //---------- Start: CancelAllNotifications ----------//

    /**
     * On click click menu option Delete all
     * Cancel all notifications for reminder by all ID
     */
    private void cancelAllNotifications() {
        ArrayList<String> allRemindersID = remindersItemsList.getRemindersId();
        ArrayList<String> remindersContent = remindersItemsList.getRemindersContent();

        ArrayList<String> remindersYear = remindersItemsList.getRemindersYear();
        ArrayList<String> remindersMonth = remindersItemsList.getRemindersMonth();
        ArrayList<String> remindersDay = remindersItemsList.getRemindersDay();

        ArrayList<String> remindersHour = remindersItemsList.getRemindersHour();
        ArrayList<String> remindersMinute = remindersItemsList.getRemindersMinute();

        setCreateNotification(allRemindersID, remindersContent, remindersYear, remindersMonth,
                remindersDay, remindersHour, remindersMinute);
    }

    /**
     * Cancel all notifications
     */
    private void setCreateNotification(ArrayList<String> allRemindersID,
                                       ArrayList<String> remindersContent,
                                       ArrayList<String> remindersYear,
                                       ArrayList<String> remindersMonth,
                                       ArrayList<String> remindersDay,
                                       ArrayList<String> remindersHour,
                                       ArrayList<String> remindersMinute) {
        int index = 0;
        for (String reminderId : allRemindersID) {
            CreateNotification createNotification = new CreateNotification(
                    getApplicationContext(), reminderId, remindersContent.get(index),
                    Integer.parseInt(remindersYear.get(index)),
                    Integer.parseInt(remindersMonth.get(index)),
                    Integer.parseInt(remindersDay.get(index)),
                    Integer.parseInt(remindersHour.get(index)),
                    Integer.parseInt(remindersMinute.get(index)));
            index++;
            // Cancel notification by id
            createNotification.cancelNotification();
        }
    }

    //---------- End: CancelAllNotifications ----------//


    /**
     * On click menu option Delete all
     */
    private void setIntentMainReminders() {
        Intent intentMainReminders = new Intent(this, MainRemindersActivity.class);
        startActivity(intentMainReminders);
    }

    //---------- End: DeleteAll ----------//


    /**
     * On click menu option Notifications
     */
    private void setIntentNotificationsSettings() {
        Intent intentNotificationsSettings = new Intent(getApplicationContext(),
                NotificationsSettingsActivity.class);
        startActivity(intentNotificationsSettings);
    }

    //---------- End: SetBottomNavigationView ----------//
}
