package com.finalproject.mysac.data.local.db;

import static com.finalproject.mysac.data.local.db.DbContract.ResepEntry.TABLE_RESEP;
import static com.finalproject.mysac.data.local.db.DbContract.UserEntry.TABLE_USER;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    // DB Meta
    public static String DATABASE_NAME = "db_mysac";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DbContract.UserEntry.CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(DbContract.ResepEntry.CREATE_TABLE_RESEP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RESEP);
        onCreate(sqLiteDatabase);
    }
}
