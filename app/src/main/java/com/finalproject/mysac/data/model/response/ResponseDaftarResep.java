package com.finalproject.mysac.data.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDaftarResep {
    public List<ResponseResep> getDaftarResep() {
        return daftarResep;
    }

    public void setDaftarResep(List<ResponseResep> daftarResep) {
        this.daftarResep = daftarResep;
    }

    @SerializedName("meals")
    List<ResponseResep> daftarResep;

}
