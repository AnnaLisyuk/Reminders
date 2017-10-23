package com.example.android.remindersapplication.dataBase.dataBaseReminders;

import android.provider.BaseColumns;

class FeedReaderContractReminders {
    private FeedReaderContractReminders() {
    }

    static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "reminders_table";
        static final String COLUMN_CONTENT = "CONTENT";
        static final String COLUMN_YEAR = "YEAR";
        static final String COLUMN_MONTH = "MONTH";
        static final String COLUMN_DAY = "DAY";
        static final String COLUMN_HOUR = "HOUR";
        static final String COLUMN_MINUTE = "MINUTE";
    }

    static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY," +
            FeedEntry.COLUMN_CONTENT + " TEXT," +
            FeedEntry.COLUMN_YEAR + " TEXT," +
            FeedEntry.COLUMN_MONTH + " TEXT," +
            FeedEntry.COLUMN_DAY + " TEXT," +
            FeedEntry.COLUMN_HOUR + " TEXT," +
            FeedEntry.COLUMN_MINUTE + " TEXT)";

    static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}
