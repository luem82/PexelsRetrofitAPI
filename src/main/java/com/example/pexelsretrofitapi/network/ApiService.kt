package com.example.pexelsretrofitapi.network

import com.example.pexelsretrofitapi.model.pexels.PexelsCurated
import com.example.pexelsretrofitapi.model.pexels.PexelsSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: 563492ad6f9170000100000172c80e246d6140d29af6505cc6a6a0ea")
    @GET("v1/search")
    fun searchPexelsPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Call<PexelsSearch>

    @Headers("Authorization: 563492ad6f9170000100000172c80e246d6140d29af6505cc6a6a0ea")
    @GET("v1/curated")
    fun gertCurated(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Call<PexelsCurated>

}