package com.finalproject.mysac.data.retrofit;

import com.finalproject.mysac.data.model.response.ResponseDaftarResep;
import com.finalproject.mysac.data.model.response.ResponseKategori;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {

    // Get All Categories
    // URL: www.themealdb.com/api/json/v1/1/categories.php
    @GET("categories.php")
    Call<ResponseKategori> getCategoryList();

    // Get All Meals by Category
    // URL: www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    @GET("filter.php")
    Call<ResponseDaftarResep> getMealListByCategory(@Query("c") String kategori);

}
