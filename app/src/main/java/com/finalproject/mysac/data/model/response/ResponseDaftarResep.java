package com.finalproject.mysac.data.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDaftarResep {

    @SerializedName("meals")
    List<ResponseResep> daftarResep;

}
