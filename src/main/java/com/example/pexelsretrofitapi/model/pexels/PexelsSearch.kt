package com.example.pexelsretrofitapi.model.pexels


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PexelsSearch(
    @SerializedName("next_page")
    val nextPage: String,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("photos")
    val photos: List<Photo>,
    @SerializedName("total_results")
    val totalResults: Int
): Serializable