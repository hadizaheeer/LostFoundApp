package com.example.lostfoundapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lost_found.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ITEMS = "items";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ITEMS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT, " +
                "name TEXT, " +
                "phone TEXT, " +
                "description TEXT, " +
                "category TEXT, " +
                "date TEXT, " +
                "location TEXT, " +
                "imageUri TEXT, " +
                "timestamp INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    public boolean insertItem(String type, String name, String phone, String description,
                              String category, String date, String location,
                              String imageUri, long timestamp) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("type", type);
        values.put("name", name);
        values.put("phone", phone);
        values.put("description", description);
        values.put("category", category);
        values.put("date", date);
        values.put("location", location);
        values.put("imageUri", imageUri);
        values.put("timestamp", timestamp);

        long result = db.insert(TABLE_ITEMS, null, values);
        return result != -1;
    }

    public ArrayList<LostFoundItem> getAllItems() {
        ArrayList<LostFoundItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ITEMS + " ORDER BY timestamp DESC", null);

        if (cursor.moveToFirst()) {
            do {
                itemList.add(cursorToItem(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return itemList;
    }

    public ArrayList<LostFoundItem> getItemsByCategory(String category) {
        ArrayList<LostFoundItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_ITEMS + " WHERE category = ? ORDER BY timestamp DESC",
                new String[]{category}
        );

        if (cursor.moveToFirst()) {
            do {
                itemList.add(cursorToItem(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return itemList;
    }

    public LostFoundItem getItemById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_ITEMS + " WHERE id = ?",
                new String[]{String.valueOf(id)}
        );

        LostFoundItem item = null;

        if (cursor.moveToFirst()) {
            item = cursorToItem(cursor);
        }

        cursor.close();
        return item;
    }

    public boolean deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ITEMS, "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    private LostFoundItem cursorToItem(Cursor cursor) {
        return new LostFoundItem(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("type")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                cursor.getString(cursor.getColumnIndexOrThrow("description")),
                cursor.getString(cursor.getColumnIndexOrThrow("category")),
                cursor.getString(cursor.getColumnIndexOrThrow("date")),
                cursor.getString(cursor.getColumnIndexOrThrow("location")),
                cursor.getString(cursor.getColumnIndexOrThrow("imageUri")),
                cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"))
        );
    }
}