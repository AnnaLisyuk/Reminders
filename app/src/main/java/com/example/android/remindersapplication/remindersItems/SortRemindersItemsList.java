package com.example.android.remindersapplication.remindersItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class SortRemindersItemsList {
    private ReminderItems reminderItems;
    private RemindersItemsList remindersItemsList;

    private ArrayList<String> remindersId;
    private ArrayList<String> remindersContent;
    private ArrayList<String> remindersYear;
    private ArrayList<String> remindersMonth;
    private ArrayList<String> remindersDay;
    private ArrayList<String> remindersHour;
    private ArrayList<String> remindersMinute;

    public SortRemindersItemsList(RemindersItemsList remindersItemsList) {
        reminderItems = new ReminderItems();
        this.remindersItemsList = remindersItemsList;

        remindersId = this.remindersItemsList.getRemindersId();
        remindersContent = this.remindersItemsList.getRemindersContent();

        remindersYear = new ArrayList<>();
        remindersMonth = new ArrayList<>();
        remindersDay = new ArrayList<>();
        remindersHour = new ArrayList<>();
        remindersMinute = new ArrayList<>();

        splitRemindersDateAndTime();
    }

    /**
     * Split remindersDateAndTime string to day, month, year and hour, minute
     */
    private void splitRemindersDateAndTime() {
        ArrayList<String> remindersDateAndTime = remindersItemsList.getRemindersDateAndTime();

        for (String dateAndTime : remindersDateAndTime) {
            reminderItems.splitDateAndTime(dateAndTime);
            remindersYear.add(reminderItems.getYear());
            remindersMonth.add(reminderItems.getMonth());
            remindersDay.add(reminderItems.getDay());
            remindersHour.add(reminderItems.getHour());
            remindersMinute.add(reminderItems.getMinute());
        }
    }


    //---------- Start: GetSortedRemindersItemsList ----------//

    public RemindersItemsList getSortedRemindersItemsList() {
        // First sort the remindersItemsList
        // then update remindersItemsList to sorted remindersItemsList
        sortRemindersItemsList();
        return getUpdatedRemindersItemsList();
    }


    //---------- Start: SortRemindersItemsList ----------//

    private void sortRemindersItemsList() {
        // Sort remindersItemsList by date from the end to the start
        for (int i = remindersId.size() - 1; i > 0; i--)
            sortForTwoDates(i, i - 1);
    }

    /**
     * Sort two dates for current indexes
     */
    private void sortForTwoDates(int firstIndex, int secondIndex) {
        int firstYear = Integer.parseInt(remindersYear.get(firstIndex));
        int secondYear = Integer.parseInt(remindersYear.get(secondIndex));

        int firstMonth = Integer.parseInt(remindersMonth.get(firstIndex));
        int secondMonth = Integer.parseInt(remindersMonth.get(secondIndex));

        int firstDay = Integer.parseInt(remindersDay.get(firstIndex));
        int secondDay = Integer.parseInt(remindersDay.get(secondIndex));

        int firstHour = Integer.parseInt(remindersHour.get(firstIndex));
        int secondHour = Integer.parseInt(remindersHour.get(secondIndex));

        int firstMinute = Integer.parseInt(remindersMinute.get(firstIndex));
        int secondMinute = Integer.parseInt(remindersMinute.get(secondIndex));

        compareBetweenTwoDates(firstIndex, secondIndex, firstYear, secondYear, firstMonth,
                secondMonth, firstDay, secondDay, firstHour, secondHour, firstMinute, secondMinute);
    }


    //---------- Start: CompareBetweenTwoDates ----------//

    /**
     * Compare between two dates for current indexes
     */
    private void compareBetweenTwoDates(int firstIndex, int secondIndex, int firstYear, int secondYear,
                                        int firstMonth, int secondMonth, int firstDay, int secondDay,
                                        int firstHour, int secondHour, int firstMinute,
                                        int secondMinute) {
        Date firstDate = new Date(firstYear, firstMonth, firstDay, firstHour, firstMinute);
        Date secondDate = new Date(secondYear, secondMonth, secondDay, secondHour, secondMinute);

        // If the next date is greater than the previous date then swapped all
        if (firstDate.compareTo(secondDate) == -1)
            swappedAll(firstIndex, secondIndex);
    }

    /**
     * Swapped all reminders items
     */
    private void swappedAll(int firstIndex, int secondIndex) {
        Collections.swap(remindersId, firstIndex, secondIndex);
        Collections.swap(remindersContent, firstIndex, secondIndex);

        Collections.swap(remindersYear, firstIndex, secondIndex);
        Collections.swap(remindersMonth, firstIndex, secondIndex);
        Collections.swap(remindersDay, firstIndex, secondIndex);

        Collections.swap(remindersHour, firstIndex, secondIndex);
        Collections.swap(remindersMinute, firstIndex, secondIndex);
    }

    //---------- End: CompareBetweenTwoDates ----------//

    //---------- End: SortRemindersItemsList ----------//


    //---------- Start: GetUpdatedRemindersItemsList ----------//

    /**
     * Get updated remindersItemsList
     */
    private RemindersItemsList getUpdatedRemindersItemsList() {
        remindersItemsList = new RemindersItemsList();

        for (int i = 0; i < remindersId.size(); i++)
            setRemindersItemsList(i);

        return remindersItemsList;
    }

    private void setRemindersItemsList(int index) {
        remindersItemsList.addId(remindersId.get(index));
        remindersItemsList.addContent(remindersContent.get(index));
        // Add year, month and day
        remindersItemsList.addDate(remindersYear.get(index), remindersMonth.get(index),
                remindersDay.get(index));
        // Add hour and minute
        remindersItemsList.addTime(remindersHour.get(index), remindersMinute.get(index));
    }

    //---------- End: GetUpdatedRemindersItemsList ----------//

    //---------- End: GetSortedRemindersItemsList ----------//
}
