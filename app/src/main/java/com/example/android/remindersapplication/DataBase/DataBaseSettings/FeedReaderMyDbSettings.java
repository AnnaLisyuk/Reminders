package com.example.android.remindersapplication.DataBase.DataBaseSettings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FeedReaderMyDbSettings {
    private FeedReaderDbHelperSettings dbHelper;

    public FeedReaderMyDbSettings(Context context) {
        dbHelper = new FeedReaderDbHelperSettings(context);
    }

    /**
     * Put data into a database
     */
    public void insertData(String sound, String vibrate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContractSettings.FeedEntry.COLUMN_SOUND, sound);
        values.put(FeedReaderContractSettings.FeedEntry.COLUMN_VIBRATE, vibrate);

        db.insert(FeedReaderContractSettings.FeedEntry.TABLE_NAME, null, values);
    }

    /**
     * Read data from a database
     */
    public Cursor readData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.rawQuery("select * from " + FeedReaderContractSettings.FeedEntry.TABLE_NAME,
                null);
    }

    /**
     * Update a database
     */
    public void updateData(String id, String sound, String vibrate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContractSettings.FeedEntry._ID, id);
        values.put(FeedReaderContractSettings.FeedEntry.COLUMN_SOUND, sound);
        values.put(FeedReaderContractSettings.FeedEntry.COLUMN_VIBRATE, vibrate);

        // Which row to update
        String selection = FeedReaderContractSettings.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = {id};

        db.update(FeedReaderContractSettings.FeedEntry.TABLE_NAME, values, selection,
                selectionArgs);
    }
}
