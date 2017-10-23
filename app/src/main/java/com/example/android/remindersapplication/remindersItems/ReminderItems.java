package com.example.android.remindersapplication.remindersItems;

public class ReminderItems {
    private String content;
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHour() {
        return hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getMinute() {
        return minute;
    }


    //---------- Start: CheckReminderInput ----------//

    /**
     * Check if the input is valid
     */
    public boolean checkReminderInput() {
        return !(!checkDateInput() || !checkTimeInput());
    }

    /**
     * Check if date input is valid
     */
    private boolean checkDateInput() {
        return !(year == null || month == null || day == null);
    }

    /**
     * Check if time input is valid
     */
    private boolean checkTimeInput() {
        return !(hour == null || minute == null);
    }

    //---------- End: CheckReminderInput ----------//


    //---------- Start: SplitDateAndTime ----------//

    /**
     * Split the date and time string
     */
    public void splitDateAndTime(String reminderDateAndTime) {
        String[] dateAndTime = reminderDateAndTime.split(" ");
        splitDate(dateAndTime[0]);
        splitTime(dateAndTime[1]);
    }

    /**
     * Split the date string to day, month, year
     */
    private void splitDate(String reminderDate) {
        String[] date = reminderDate.split("/");
        day = date[0];
        month = date[1];
        year = date[2];
    }

    /**
     * Split the time string to hour, minute
     */
    private void splitTime(String reminderTime) {
        String[] time = reminderTime.split(":");
        hour = time[0];
        minute = time[1];
    }

    //---------- End: SplitDateAndTime ----------//
}
