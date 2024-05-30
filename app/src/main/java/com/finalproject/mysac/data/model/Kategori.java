package com.finalproject.mysac.data.model;

import com.google.gson.annotations.SerializedName;

public class Kategori {

    @SerializedName("idCategori")
    private int id;

    @SerializedName("strCategory")
    private String nama;

    @SerializedName("strCategoryThumb")
    private String gambar;

    @SerializedName("strCategoryDescription")
    private String deskripsi;

    public Kategori(int id, String nama, String gambar, String deskripsi) {
        this.id = id;
        this.nama = nama;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
    }
}
