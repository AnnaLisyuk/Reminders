package com.example.android.remindersapplication.DataBase.DataBaseReminders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FeedReaderMyDbReminders {
    private FeedReaderDbHelperReminders dbHelper;

    public FeedReaderMyDbReminders(Context context) {
        dbHelper = new FeedReaderDbHelperReminders(context);
    }

    /**
     * Put data into a database
     * and return the row ID
     */
    public long insertData(String content, String year, String month, String day,
                           String hour, String minute) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContractReminders.FeedEntry.COLUMN_CONTENT, content);
        values.put(FeedReaderContractReminders.FeedEntry.COLUMN_YEAR, year);
        values.put(FeedReaderContractReminders.FeedEntry.COLUMN_MONTH, month);
        values.put(FeedReaderContractReminders.FeedEntry.COLUMN_DAY, day);
        values.put(FeedReaderContractReminders.FeedEntry.COLUMN_HOUR, hour);
        values.put(FeedReaderContractReminders.FeedEntry.COLUMN_MINUTE, minute);

        // Return the row ID
        return db.insert(FeedReaderContractReminders.FeedEntry.TABLE_NAME, null, values);
    }

    /**
     * Read data from a database
     */
    public Cursor readData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.rawQuery("select * from " + FeedReaderContractReminders.FeedEntry.TABLE_NAME,
                null);
    }

    /**
     * Update a database
     */
    public void updateData(String id, String content, String year, String month, String day,
                           String hour, String minute) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContractReminders.FeedEntry._ID, id);
        values.put(FeedReaderContractReminders.FeedEntry.COLUMN_CONTENT, content);
        values.put(FeedReaderContractReminders.FeedEntry.COLUMN_YEAR, year);
        values.put(FeedReaderContractReminders.FeedEntry.COLUMN_MONTH, month);
        values.put(FeedReaderContractReminders.FeedEntry.COLUMN_DAY, day);
        values.put(FeedReaderContractReminders.FeedEntry.COLUMN_HOUR, hour);
        values.put(FeedReaderContractReminders.FeedEntry.COLUMN_MINUTE, minute);

        // Which row to update
        String selection = FeedReaderContractReminders.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = {id};

        db.update(FeedReaderContractReminders.FeedEntry.TABLE_NAME, values,
                selection, selectionArgs);
    }

    /**
     * Delete data from a database
     */
    public boolean deleteData(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Which row to delete
        String selection = FeedReaderContractReminders.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = {id};
        return db.delete(FeedReaderContractReminders.FeedEntry.TABLE_NAME, selection, selectionArgs)
                > 0;
    }

    /**
     * Delete all data from a database
     */
    public boolean deleteAllData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.delete(FeedReaderContractReminders.FeedEntry.TABLE_NAME, null, null) > 0;
    }
}
