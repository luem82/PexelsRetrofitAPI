package com.example.pexelsretrofitapi.model.pexels


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Photo(
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("liked")
    val liked: Boolean,
    @SerializedName("photographer")
    val photographer: String,
    @SerializedName("photographer_id")
    val photographerId: Int,
    @SerializedName("photographer_url")
    val photographerUrl: String,
    @SerializedName("src")
    val src: Src?,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
) : Serializable