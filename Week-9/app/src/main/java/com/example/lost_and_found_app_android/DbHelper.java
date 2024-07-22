package com.example.lost_and_found_app_android;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public static final String TABLE_ITEMS_LOST_AND_FOUND = "TABLE_ITEMS_LOST_AND_FOUND";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
    public static final String DATE = "DATE";
    public static final String IS_DELETED = "IS_DELETED";
    public static final String ITEM_LOCATION = "ITEM_LOCATION";
    public static final String ITEM_LAT = "ITEM_LAT";
    public static final String ITEM_LNG = "ITEM_LNG";
    public static final String LOST_OR_FOUND = "LOST_OR_FOUND";
    private int dbVersion = 4;

    public DbHelper(@Nullable Context context) {
        super(context, "table_items_lost_and_found", null, 3);
    }

    @Override // Create table on activity start.
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTable = "CREATE TABLE " + TABLE_ITEMS_LOST_AND_FOUND + " (" + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME + " VARCHAR(25) NOT NULL, " + PHONE_NUMBER + " INTEGER(15) NOT NULL, " + ITEM_DESCRIPTION + " VARCHAR(255) NOT NULL, " + DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, " + IS_DELETED + " TINYINT DEFAULT 0, " + ITEM_LOCATION + " VARCHAR(100) NOT NULL DEFAULT NULL, " + LOST_OR_FOUND + " VARCHAR(10) NOT NULL DEFAULT NULL)";
        db.execSQL(queryCreateTable);
    }

    @Override // Called when Database schema changes in the code - will sync this change with the local Db instance.
    public void onUpgrade(SQLiteDatabase db, int i, int x) {
        if (dbVersion == 4) {
            db.execSQL("ALTER TABLE " + TABLE_ITEMS_LOST_AND_FOUND + " ADD COLUMN " + ITEM_LAT + " INTEGER DEFAULT NULL");
            db.execSQL("ALTER TABLE " + TABLE_ITEMS_LOST_AND_FOUND + " ADD COLUMN " + ITEM_LNG + " INTEGER DEFAULT NULL");
        }
    }
    public boolean createLostOrFoundItemRecord(LostAndFoundModel itemRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues associativeArray = new ContentValues();

        // UPDATE the associativeArray with data for creating a new record.
        associativeArray.put(USER_NAME, itemRecord.getUserName());
        associativeArray.put(PHONE_NUMBER, itemRecord.getPhoneNumber());
        associativeArray.put(ITEM_DESCRIPTION, itemRecord.getItemDescription());
        associativeArray.put(DATE, itemRecord.getDate());
        associativeArray.put(ITEM_LAT, itemRecord.getItemLocationLat());
        associativeArray.put(ITEM_LOCATION, itemRecord.getItemLocation());
        associativeArray.put(ITEM_LNG, itemRecord.getItemLocationLng());
        associativeArray.put(LOST_OR_FOUND, itemRecord.getLostOrFound());

        long insert = db.insert(TABLE_ITEMS_LOST_AND_FOUND, null, associativeArray);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }
    public ArrayList getItemLat() {
        ArrayList itemLatList = new ArrayList<>();

        String selectItemLat = "SELECT `ITEM_LAT` FROM " + TABLE_ITEMS_LOST_AND_FOUND;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectItemLat, null);

        if (cursor.moveToFirst()) {
            do {
                Double itemLat = cursor.getDouble(0);
                itemLatList.add(itemLat);
            } while (cursor.moveToNext());
        } else {
            // No more items in list.
        }

        cursor.close();
        db.close();
        return itemLatList;
    }
    public ArrayList getItemLng() {
        ArrayList itemLngList = new ArrayList<>();

        String selectItemLat = "SELECT `ITEM_LNG` FROM " + TABLE_ITEMS_LOST_AND_FOUND;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectItemLat, null);

        if (cursor.moveToFirst()) {
            do {
                Double itemLng = cursor.getDouble(0);
                itemLngList.add(itemLng);
            } while (cursor.moveToNext());
        } else {
            // No more items in list.
        }

        cursor.close();
        db.close();
        return itemLngList;
    }

    public ArrayList getItemLocation() {
        ArrayList itemLocationList = new ArrayList<>();

        String selectItemLocation = "SELECT `ITEM_LOCATION` FROM " + TABLE_ITEMS_LOST_AND_FOUND;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectItemLocation, null);

        if (cursor.moveToFirst()) {
            do {
                String itemLocation = cursor.getString(0);
                itemLocationList.add(itemLocation);
            } while (cursor.moveToNext());
        } else {
            // No more items in list.
        }

        cursor.close();
        db.close();
        return itemLocationList;
    }
    public ArrayList getItemDescription() {
        ArrayList itemNameList = new ArrayList<>();

        String selectItemName = "SELECT `ITEM_DESCRIPTION` FROM " + TABLE_ITEMS_LOST_AND_FOUND;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectItemName, null);

        if (cursor.moveToFirst()) {
            do {
                String itemLocation = cursor.getString(0);
                itemNameList.add(itemLocation);
            } while (cursor.moveToNext());
        } else {
            // No more items in list.
        }

        cursor.close();
        db.close();
        return itemNameList;
    }
    public List<LostAndFoundModel> getAllItemRecords() {
        // Collection of records represented as a dynamic array/vector.
        ArrayList<LostAndFoundModel> itemCollection = new ArrayList<>();

        // Make SELECT query
        String selectAll = "SELECT * FROM " + TABLE_ITEMS_LOST_AND_FOUND;
        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor = 'result set' data-type
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) { // If cursor contains a value
            // iterate through the returned result set and add to the itemCollection
            do {
                int itemID = cursor.getInt(0);
                String userName = cursor.getString(1);
                int phoneNumber = cursor.getInt(2);
                String itemDescription = cursor.getString(3);
                String date = cursor.getString(4);
                boolean isDeleted = cursor.getInt(5) == 1 ? true: false;
                String itemLocation = cursor.getString(6);
                String lostOrFoundPost = cursor.getString(7);
                double itemLocationLng = cursor.getDouble(8);
                double itemLocationLat = cursor.getDouble(9);

                LostAndFoundModel newItemRecord = new LostAndFoundModel(itemID, userName, phoneNumber, itemDescription, date, isDeleted, itemLocationLat, itemLocation, itemLocationLng, lostOrFoundPost);
                itemCollection.add(newItemRecord);
            } while (cursor.moveToNext());

        } else {
            // Do not add anything to the list.
        }
        // Close connection to the Db.
        cursor.close();
        db.close();
        return itemCollection;
    }
    public boolean deleteLostOrFoundItemRecord(LostAndFoundModel itemRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQueryStr = "DELETE FROM " + TABLE_ITEMS_LOST_AND_FOUND + " WHERE " + ITEM_ID + " = " + itemRecord.getItemId();
        Cursor cursor = db.rawQuery(deleteQueryStr, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }
}
