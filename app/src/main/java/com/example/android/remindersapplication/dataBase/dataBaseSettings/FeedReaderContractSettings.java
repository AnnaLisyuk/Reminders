package com.example.android.remindersapplication.dataBase.dataBaseSettings;

import android.provider.BaseColumns;

class FeedReaderContractSettings {
    private FeedReaderContractSettings() {
    }

    static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "settings_table";
        static final String COLUMN_SOUND = "SOUND";
        static final String COLUMN_VIBRATE = "VIBRATE";
    }

    static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY," +
            FeedEntry.COLUMN_SOUND + " TEXT," +
            FeedEntry.COLUMN_VIBRATE + " TEXT)";

    static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}
