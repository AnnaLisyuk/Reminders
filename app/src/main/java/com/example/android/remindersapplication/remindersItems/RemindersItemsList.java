package com.example.android.remindersapplication.remindersItems;

import java.util.ArrayList;
import java.util.Iterator;

public class RemindersItemsList {
    private ArrayList<String> remindersId;
    private ArrayList<String> remindersContent;

    private ArrayList<String> remindersDate;
    private ArrayList<String> remindersYear;
    private ArrayList<String> remindersMonth;
    private ArrayList<String> remindersDay;

    private ArrayList<String> remindersTime;
    private ArrayList<String> remindersHour;
    private ArrayList<String> remindersMinute;

    private ArrayList<String> remindersDateAndTime;

    public RemindersItemsList() {
        remindersId = new ArrayList<>();
        remindersContent = new ArrayList<>();

        remindersDate = new ArrayList<>();
        remindersYear = new ArrayList<>();
        remindersMonth = new ArrayList<>();
        remindersDay = new ArrayList<>();

        remindersTime = new ArrayList<>();
        remindersHour = new ArrayList<>();
        remindersMinute = new ArrayList<>();

        remindersDateAndTime = new ArrayList<>();
    }

    public void addId(String id) {
        remindersId.add(id);
    }

    public ArrayList<String> getRemindersId() {
        return remindersId;
    }

    public void addContent(String content) {
        remindersContent.add(content);
    }

    public ArrayList<String> getRemindersContent() {
        return remindersContent;
    }


    //---------- Start: AddDate ----------//

    public void addDate(String year, String month, String day) {
        remindersDate.add(combineStringDate(year, month, day));
        remindersYear.add(year);
        remindersMonth.add(month);
        remindersDay.add(day);
    }

    /**
     * Combine string date
     */
    public String combineStringDate(String year, String month, String day) {
        return day + "/" + month + "/" + year;
    }

    //---------- End: AddDate ----------//


    //---------- Start: GetDate ----------//

    public ArrayList<String> getRemindersYear() {
        return remindersYear;
    }

    public ArrayList<String> getRemindersMonth() {
        return remindersMonth;
    }

    public ArrayList<String> getRemindersDay() {
        return remindersDay;
    }

    //---------- End: GetDate ----------//


    //---------- Start: AddTime ----------//

    public void addTime(String hour, String minute) {
        remindersTime.add(combineStringTime(hour, minute));
        remindersHour.add(hour);
        remindersMinute.add(minute);
    }

    /**
     * Combine string time
     */
    public String combineStringTime(String hour, String minute) {
        return hour + ":" + minute;
    }

    //---------- End: AddTime ----------//


    //---------- Start: GetTime ----------//

    public ArrayList<String> getRemindersHour() {
        return remindersHour;
    }

    public ArrayList<String> getRemindersMinute() {
        return remindersMinute;
    }

    //---------- End: GetTime ----------//


    //---------- Start: GetRemindersDateAndTime ----------//

    public ArrayList<String> getRemindersDateAndTime() {
        // Add all date and time to remindersDateAndTime list
        addDateAndTime();
        return remindersDateAndTime;
    }


    //---------- Start: AddDateAndTime ----------//

    /**
     * Combine all remindersDate list and remindersTime list to remindersDateAndTime list
     */
    private void addDateAndTime() {
        Iterator<String> dateIterator = remindersDate.iterator();
        Iterator<String> timeIterator = remindersTime.iterator();
        while (dateIterator.hasNext() && timeIterator.hasNext()) {
            // Combine remindersDate list and remindersTime list to DateAndTime format list
            remindersDateAndTime.add(combineStringDateAndTime(dateIterator.next(),
                    timeIterator.next()));
        }
    }

    /**
     * Combine string date and time
     */
    public String combineStringDateAndTime(String date, String time) {
        return date + " " + time;
    }

    //---------- End: AddDateAndTime ----------//

    //---------- End: GetReminderDateAndTime ----------//
}
