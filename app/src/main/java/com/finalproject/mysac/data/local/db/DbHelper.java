package com.finalproject.mysac.data.local.db;

import static com.finalproject.mysac.data.local.db.DbContract.ResepEntry.TABLE_RESEP;
import static com.finalproject.mysac.data.local.db.DbContract.UserEntry.KEY_USER_BIO;
import static com.finalproject.mysac.data.local.db.DbContract.UserEntry.KEY_USER_JUMLAH_RESEP;
import static com.finalproject.mysac.data.local.db.DbContract.UserEntry.KEY_USER_LINK_FB;
import static com.finalproject.mysac.data.local.db.DbContract.UserEntry.KEY_USER_LINK_IG;
import static com.finalproject.mysac.data.local.db.DbContract.UserEntry.KEY_USER_LINK_YT;
import static com.finalproject.mysac.data.local.db.DbContract.UserEntry.KEY_USER_NAME;
import static com.finalproject.mysac.data.local.db.DbContract.UserEntry.KEY_USER_PASSWORD;
import static com.finalproject.mysac.data.local.db.DbContract.UserEntry.KEY_USER_PHOTO;
import static com.finalproject.mysac.data.local.db.DbContract.UserEntry.KEY_USER_USERNAME;
import static com.finalproject.mysac.data.local.db.DbContract.UserEntry.TABLE_USER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    public long registerUser(String username, String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME, username);
        values.put(KEY_USER_NAME, name);
        values.put(KEY_USER_PASSWORD, password);
        values.put(KEY_USER_BIO, "");
        values.put(KEY_USER_JUMLAH_RESEP, 0);
        values.put(KEY_USER_LINK_FB, "");
        values.put(KEY_USER_LINK_IG, "");
        values.put(KEY_USER_LINK_YT, "");
        values.put(KEY_USER_PHOTO, (Byte) null);
        return db.insert(TABLE_USER, null, values);
    }

    public boolean login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { KEY_USER_USERNAME };
        String selection = KEY_USER_USERNAME + " = ? AND " + KEY_USER_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);

        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close();
        return isAuthenticated;
    }
}
