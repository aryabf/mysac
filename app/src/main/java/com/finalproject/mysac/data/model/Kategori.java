package com.finalproject.mysac.data.model;

import com.google.gson.annotations.SerializedName;

public class Kategori {

    @SerializedName("idCategori")
    int id;

    @SerializedName("strCategory")
    String nama;

    @SerializedName("strCategoryThumb")
    String gambar;

    @SerializedName("strCategoryDescription")
    String deskripsi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Kategori(int id, String nama, String gambar, String deskripsi) {
        this.id = id;
        this.nama = nama;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
    }
}
