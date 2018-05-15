package com.example.raymaletdin.nativeserver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "TAG_" + DBHelper.class.getSimpleName();

    private final static String DATABASE   = "myDb.db";
    public final static String  TABLE      = "addressBook";
    public final static String  ID         = "id";
    public final static String  FIRST_NAME = "firstName";
    public final static String  LAST_NAME  = "lastName";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, DATABASE, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table '" + TABLE + "' ("
                + ID + " integer primary key autoincrement,"
                + FIRST_NAME + " text,"
                + LAST_NAME + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
