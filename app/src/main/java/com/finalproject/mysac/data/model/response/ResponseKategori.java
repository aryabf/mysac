package com.finalproject.mysac.data.model.response;

import com.finalproject.mysac.data.model.Kategori;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseKategori {

    public ArrayList<Kategori> getKategori() {
        return kategori;
    }

    public void setKategori(ArrayList<Kategori> kategori) {
        this.kategori = kategori;
    }

    @SerializedName("categories")
    ArrayList<Kategori> kategori;

}
