package com.finalproject.mysac.data.local.db;

import static com.finalproject.mysac.data.local.db.DbContract.ResepEntry.KEY_RESEP_AREA;
import static com.finalproject.mysac.data.local.db.DbContract.ResepEntry.KEY_RESEP_BAHAN;
import static com.finalproject.mysac.data.local.db.DbContract.ResepEntry.KEY_RESEP_GAMBAR;
import static com.finalproject.mysac.data.local.db.DbContract.ResepEntry.KEY_RESEP_GAMBAR_PEMBUAT;
import static com.finalproject.mysac.data.local.db.DbContract.ResepEntry.KEY_RESEP_ID;
import static com.finalproject.mysac.data.local.db.DbContract.ResepEntry.KEY_RESEP_INSTRUKSI;
import static com.finalproject.mysac.data.local.db.DbContract.ResepEntry.KEY_RESEP_KATEGORI;
import static com.finalproject.mysac.data.local.db.DbContract.ResepEntry.KEY_RESEP_NAMA;
import static com.finalproject.mysac.data.local.db.DbContract.ResepEntry.KEY_RESEP_NAMA_PEMBUAT;
import static com.finalproject.mysac.data.local.db.DbContract.ResepEntry.KEY_RESEP_PEMBUAT;
import static com.finalproject.mysac.data.local.db.DbContract.ResepEntry.KEY_RESEP_TAKARAN;
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
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.finalproject.mysac.data.model.Kategori;
import com.finalproject.mysac.data.model.Resep;
import com.finalproject.mysac.data.model.User;
import com.finalproject.mysac.utils.ArrayListStringUtils;

import java.net.InetSocketAddress;
import java.util.ArrayList;

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

    public long createRecipe(String id, String nama, String kategori, String instruksi, String pembuat, String namaPembuat, String area, String bahan, String takaran, byte[] gambar, byte[] gambarPembuat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RESEP_ID, id);
        values.put(KEY_RESEP_KATEGORI, kategori);
        values.put(KEY_RESEP_NAMA, nama);
        values.put(KEY_RESEP_INSTRUKSI, instruksi);
        values.put(KEY_RESEP_PEMBUAT, pembuat);
        values.put(KEY_RESEP_NAMA_PEMBUAT, namaPembuat);
        values.put(KEY_RESEP_AREA, area);
        values.put(KEY_RESEP_BAHAN, bahan);
        values.put(KEY_RESEP_TAKARAN, takaran);
        values.put(KEY_RESEP_GAMBAR, gambar);
        values.put(KEY_RESEP_GAMBAR_PEMBUAT, gambarPembuat);

        try {
            return db.insertOrThrow(TABLE_RESEP, null, values);
        } catch (SQLiteConstraintException e) {
            return -1;
        }
    }

    public long updateRecipe(String id, String nama, String kategori, String instruksi, String pembuat, String namaPembuat, String area, String bahan, String takaran, byte[] gambar, byte[] gambarPembuat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RESEP_KATEGORI, kategori);
        values.put(KEY_RESEP_NAMA, nama);
        values.put(KEY_RESEP_INSTRUKSI, instruksi);
        values.put(KEY_RESEP_PEMBUAT, pembuat);
        values.put(KEY_RESEP_NAMA_PEMBUAT, namaPembuat);
        values.put(KEY_RESEP_AREA, area);
        values.put(KEY_RESEP_BAHAN, bahan);
        values.put(KEY_RESEP_TAKARAN, takaran);
        values.put(KEY_RESEP_GAMBAR, gambar);
        values.put(KEY_RESEP_GAMBAR_PEMBUAT, gambarPembuat);

        try {
            return db.update(TABLE_RESEP, values, KEY_RESEP_ID + " = ?", new String[]{id});
        } catch (SQLiteConstraintException e) {
            return -1;
        }
    }

    public int deleteRecipe(String recipeId) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String selection = KEY_RESEP_ID + " = ?";
            String[] selectionArgs = { recipeId };
            return db.delete(TABLE_RESEP, selection, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<Resep> getAllRecipes() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                KEY_RESEP_ID,
                KEY_RESEP_NAMA,
                KEY_RESEP_KATEGORI,
                KEY_RESEP_INSTRUKSI,
                KEY_RESEP_PEMBUAT,
                KEY_RESEP_NAMA_PEMBUAT,
                KEY_RESEP_AREA,
                KEY_RESEP_BAHAN,
                KEY_RESEP_TAKARAN,
                KEY_RESEP_GAMBAR,
                KEY_RESEP_GAMBAR_PEMBUAT
        };

        Cursor cursor = db.query(
                TABLE_RESEP,    // Tabel
                columns,       // Kolom yang akan diambil
                null,          // Tanpa WHERE clause
                null,          // Tanpa nilai untuk WHERE clause
                null,          // Group by
                null,          // Having
                null           // Order by
        );

        ArrayList<Resep> recipes = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String idResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_ID));
                String namaResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_NAMA));
                String kategoriResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_KATEGORI));
                String instruksiResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_INSTRUKSI));
                String pembuatResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_PEMBUAT));
                String namaPembuatResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_NAMA_PEMBUAT));
                String areaResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_AREA));
                String bahanResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_BAHAN));
                String takaranResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_TAKARAN));
                byte[] gambarResep = cursor.getBlob(cursor.getColumnIndexOrThrow(KEY_RESEP_GAMBAR));
                byte[] gambarPembuat = cursor.getBlob(cursor.getColumnIndexOrThrow(KEY_RESEP_GAMBAR_PEMBUAT));

                Log.d("DbHelper", "Photo Length: " + (gambarResep != null ? gambarResep.length : "null"));

                Resep resep = new Resep(idResep, namaResep, kategoriResep, instruksiResep, null, pembuatResep, areaResep, gambarResep, gambarPembuat, namaPembuatResep);
                resep.setBahanBahan(ArrayListStringUtils.rawDataToArrayList(bahanResep));
                resep.setUkuranUkuran(ArrayListStringUtils.rawDataToArrayList(takaranResep));

                recipes.add(resep);
            }
            cursor.close();
        }

        return recipes;
    }

    public ArrayList<Resep> getRecipesByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                KEY_RESEP_ID,
                KEY_RESEP_NAMA,
                KEY_RESEP_KATEGORI,
                KEY_RESEP_INSTRUKSI,
                KEY_RESEP_PEMBUAT,
                KEY_RESEP_NAMA_PEMBUAT,
                KEY_RESEP_AREA,
                KEY_RESEP_BAHAN,
                KEY_RESEP_TAKARAN,
                KEY_RESEP_GAMBAR,
                KEY_RESEP_GAMBAR_PEMBUAT
        };

        String selection = KEY_RESEP_KATEGORI + " = ?";
        String[] selectionArgs = {category};

        Cursor cursor = db.query(
                TABLE_RESEP,    // Tabel
                columns,       // Kolom yang akan diambil
                selection,     // WHERE clause
                selectionArgs, // Nilai untuk WHERE clause
                null,          // Group by
                null,          // Having
                null           // Order by
        );

        ArrayList<Resep> recipes = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String idResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_ID));
                String namaResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_NAMA));
                String kategoriResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_KATEGORI));
                String instruksiResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_INSTRUKSI));
                String pembuatResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_PEMBUAT));
                String namaPembuatResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_NAMA_PEMBUAT));
                String areaResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_AREA));
                String bahanResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_BAHAN));
                String takaranResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_TAKARAN));
                byte[] gambarResep = cursor.getBlob(cursor.getColumnIndexOrThrow(KEY_RESEP_GAMBAR));
                byte[] gambarPembuat = cursor.getBlob(cursor.getColumnIndexOrThrow(KEY_RESEP_GAMBAR_PEMBUAT));

                Log.d("DbHelper", "Photo Length: " + (gambarResep != null ? gambarResep.length : "null"));

                Resep resep = new Resep(idResep, namaResep, kategoriResep, instruksiResep, null, pembuatResep, areaResep, gambarResep, gambarPembuat, namaPembuatResep);
                resep.setBahanBahan(ArrayListStringUtils.rawDataToArrayList(bahanResep));
                resep.setUkuranUkuran(ArrayListStringUtils.rawDataToArrayList(takaranResep));

                recipes.add(resep);
            }
            cursor.close();
        }

        return recipes;
    }

    public ArrayList<Resep> getRecipesByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                KEY_RESEP_ID,
                KEY_RESEP_NAMA,
                KEY_RESEP_KATEGORI,
                KEY_RESEP_INSTRUKSI,
                KEY_RESEP_PEMBUAT,
                KEY_RESEP_NAMA_PEMBUAT,
                KEY_RESEP_AREA,
                KEY_RESEP_BAHAN,
                KEY_RESEP_TAKARAN,
                KEY_RESEP_GAMBAR,
                KEY_RESEP_GAMBAR_PEMBUAT
        };

        String selection = KEY_RESEP_PEMBUAT + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                TABLE_RESEP,    // Tabel
                columns,       // Kolom yang akan diambil
                selection,     // WHERE clause
                selectionArgs, // Nilai untuk WHERE clause
                null,          // Group by
                null,          // Having
                null           // Order by
        );

        ArrayList<Resep> recipes = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String idResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_ID));
                String namaResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_NAMA));
                String kategoriResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_KATEGORI));
                String instruksiResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_INSTRUKSI));
                String pembuatResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_PEMBUAT));
                String namaPembuatResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_NAMA_PEMBUAT));
                String areaResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_AREA));
                String bahanResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_BAHAN));
                String takaranResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_TAKARAN));
                byte[] gambarResep = cursor.getBlob(cursor.getColumnIndexOrThrow(KEY_RESEP_GAMBAR));
                byte[] gambarPembuat = cursor.getBlob(cursor.getColumnIndexOrThrow(KEY_RESEP_GAMBAR_PEMBUAT));

                Log.d("DbHelper", "Photo Length: " + (gambarResep != null ? gambarResep.length : "null"));

                Resep resep = new Resep(idResep, namaResep, kategoriResep, instruksiResep, null, pembuatResep, areaResep, gambarResep, gambarPembuat, namaPembuatResep);
                resep.setBahanBahan(ArrayListStringUtils.rawDataToArrayList(bahanResep));
                resep.setUkuranUkuran(ArrayListStringUtils.rawDataToArrayList(takaranResep));

                recipes.add(resep);
            }
            cursor.close();
        }

        return recipes;
    }

    public Resep getRecipeById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                KEY_RESEP_ID,
                KEY_RESEP_NAMA,
                KEY_RESEP_KATEGORI,
                KEY_RESEP_INSTRUKSI,
                KEY_RESEP_PEMBUAT,
                KEY_RESEP_NAMA_PEMBUAT,
                KEY_RESEP_AREA,
                KEY_RESEP_BAHAN,
                KEY_RESEP_TAKARAN,
                KEY_RESEP_GAMBAR,
                KEY_RESEP_GAMBAR_PEMBUAT
        };

        String selection = KEY_RESEP_ID + " = ?";
        String[] selectionArgs = {id};

        Cursor cursor = db.query(
                TABLE_RESEP,    // Tabel
                columns,       // Kolom yang akan diambil
                selection,     // WHERE clause
                selectionArgs, // Nilai untuk WHERE clause
                null,          // Group by
                null,          // Having
                null           // Order by
        );

        Resep resep = null;

        if (cursor != null) {
            if (cursor.moveToNext()) {
                String idResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_ID));
                String namaResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_NAMA));
                String kategoriResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_KATEGORI));
                String instruksiResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_INSTRUKSI));
                String pembuatResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_PEMBUAT));
                String namaPembuatResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_NAMA_PEMBUAT));
                String areaResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_AREA));
                String bahanResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_BAHAN));
                String takaranResep = cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESEP_TAKARAN));
                byte[] gambarResep = cursor.getBlob(cursor.getColumnIndexOrThrow(KEY_RESEP_GAMBAR));
                byte[] gambarPembuat = cursor.getBlob(cursor.getColumnIndexOrThrow(KEY_RESEP_GAMBAR_PEMBUAT));

                Log.d("DbHelper", "Photo Length: " + (gambarResep != null ? gambarResep.length : "null"));

                resep = new Resep(idResep, namaResep, kategoriResep, instruksiResep, null, pembuatResep, areaResep, gambarResep, gambarPembuat, namaPembuatResep);
                resep.setBahanBahan(ArrayListStringUtils.rawDataToArrayList(bahanResep));
                resep.setUkuranUkuran(ArrayListStringUtils.rawDataToArrayList(takaranResep));

            }
            cursor.close();
        }

        return resep;
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

        try {
            return db.insertOrThrow(TABLE_USER, null, values);
        } catch (SQLiteConstraintException e) {
            // Username sudah ada, kembalikan -1 atau pesan error
            return -1;
        }
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

                Log.d("DbHelper", "Photo Length: " + (userPhoto != null ? userPhoto.length : "null"));

                user = new User(username, userName, userPassword, userBio, userJumlahResep, userLinkFB, userLinkIG, userLinkYT, userPhoto);
            }
            cursor.close();
        }

        return user;
    }

    public int updateUser(String username, String name, String password, String bio, String linkFb, String linkIg, String linkYt, byte[] photo, int jumlahResep) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, name);
        values.put(KEY_USER_PASSWORD, password);
        values.put(KEY_USER_BIO, bio);
        values.put(KEY_USER_LINK_FB, linkFb);
        values.put(KEY_USER_LINK_IG, linkIg);
        values.put(KEY_USER_LINK_YT, linkYt);
        values.put(KEY_USER_PHOTO, photo);
        values.put(KEY_USER_JUMLAH_RESEP, jumlahResep);

        try {
            return db.update(TABLE_USER, values, KEY_USER_USERNAME + " = ?", new String[]{username});
        } catch (Exception e) {
            return -1;
        }
    }

}
