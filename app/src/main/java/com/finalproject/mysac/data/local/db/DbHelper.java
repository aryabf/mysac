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
import android.util.Log;

import com.finalproject.mysac.data.model.User;

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

    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                KEY_USER_USERNAME,
                KEY_USER_NAME,
                KEY_USER_PASSWORD,
                KEY_USER_BIO,
                KEY_USER_JUMLAH_RESEP,
                KEY_USER_LINK_FB,
                KEY_USER_LINK_IG,
                KEY_USER_LINK_YT,
                KEY_USER_PHOTO
        };

        String selection = KEY_USER_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                TABLE_USER,    // Tabel
                columns,       // Kolom yang akan diambil
                selection,     // WHERE clause
                selectionArgs, // Nilai untuk WHERE clause
                null,          // Group by
                null,          // Having
                null           // Order by
        );

        User user = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_USER_NAME));
                String userPassword = cursor.getString(cursor.getColumnIndexOrThrow(KEY_USER_PASSWORD));
                String userBio = cursor.getString(cursor.getColumnIndexOrThrow(KEY_USER_BIO));
                int userJumlahResep = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_USER_JUMLAH_RESEP));
                String userLinkFB = cursor.getString(cursor.getColumnIndexOrThrow(KEY_USER_LINK_FB));
                String userLinkIG = cursor.getString(cursor.getColumnIndexOrThrow(KEY_USER_LINK_IG));
                String userLinkYT = cursor.getString(cursor.getColumnIndexOrThrow(KEY_USER_LINK_YT));
                byte[] userPhoto = cursor.getBlob(cursor.getColumnIndexOrThrow(KEY_USER_PHOTO));

                user = new User(username, userName, userPassword, userBio, userJumlahResep, userLinkFB, userLinkIG, userLinkYT, userPhoto);
            }
            cursor.close();
        }

        return user;
    }
}
