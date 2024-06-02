package com.finalproject.mysac.data.local.db;

public class DbContract {

    // Mencegah Instansiasi
    private DbContract() {}

    public static final class UserEntry {

        static final String TABLE_USER = "users";
        static final String KEY_USER_USERNAME = "username";
        static final String KEY_USER_NAME = "name";
        static final String KEY_USER_PASSWORD = "password";
        static final String KEY_USER_BIO = "bio";
        static final String KEY_USER_JUMLAH_RESEP = "jml_resep";
        static final String KEY_USER_LINK_FB = "link_fb";
        static final String KEY_USER_LINK_IG = "link_ig";
        static final String KEY_USER_LINK_YT = "link_yt";
        static final String KEY_USER_PHOTO = "photo";

        static final String CREATE_TABLE_USERS =
                "CREATE TABLE " + TABLE_USER + "("
                        + KEY_USER_USERNAME + " TEXT PRIMARY KEY, "
                        + KEY_USER_NAME + " TEXT, "
                        + KEY_USER_PASSWORD + " TEXT, "
                        + KEY_USER_BIO + " TEXT, "
                        + KEY_USER_JUMLAH_RESEP + " TEXT, "
                        + KEY_USER_LINK_FB + " TEXT, "
                        + KEY_USER_LINK_IG + " TEXT, "
                        + KEY_USER_LINK_YT + " TEXT, "
                        + KEY_USER_PHOTO + " BLOB"
                        + ");"
                ;
    }

    public static final class ResepEntry {

        static final String TABLE_RESEP = "resep";
        static final String KEY_RESEP_ID = "id";
        static final String KEY_RESEP_NAMA = "nama";
        static final String KEY_RESEP_KATEGORI = "kategori";
        static final String KEY_RESEP_INSTRUKSI = "instruksi";
        static final String KEY_RESEP_GAMBAR = "gambar";
        static final String KEY_RESEP_GAMBAR_PEMBUAT = "gambar_pembuat";
        static final String KEY_RESEP_PEMBUAT = "pembuat";
        static final String KEY_RESEP_NAMA_PEMBUAT = "nama_pembuat";
        static final String KEY_RESEP_AREA = "area";
        static final String KEY_RESEP_BAHAN = "bahan";
        static final String KEY_RESEP_TAKARAN = "takaran";

        static final String CREATE_TABLE_RESEP =
                "CREATE TABLE " + TABLE_RESEP + "("
                        + KEY_RESEP_ID + " INT PRIMARY KEY, "
                        + KEY_RESEP_NAMA + " TEXT, "
                        + KEY_RESEP_KATEGORI + " TEXT, "
                        + KEY_RESEP_INSTRUKSI + " TEXT, "
                        + KEY_RESEP_PEMBUAT + " TEXT, "
                        + KEY_RESEP_NAMA_PEMBUAT + " TEXT, "
                        + KEY_RESEP_AREA + " TEXT, "
                        + KEY_RESEP_BAHAN + " TEXT, "
                        + KEY_RESEP_TAKARAN + " TEXT, "
                        + KEY_RESEP_GAMBAR_PEMBUAT + " BLOB, "
                        + KEY_RESEP_GAMBAR + " BLOB"
                        + ");"
                ;
    }

}
