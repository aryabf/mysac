package com.finalproject.mysac.data.model.response;

import com.finalproject.mysac.data.model.Kategori;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKategori {

    @SerializedName("categories")
    List<Kategori> kategori;

}
