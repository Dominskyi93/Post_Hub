package com.example.posthub.retrofit

import com.squareup.moshi.Json

data class Photo(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @Json(name = "download_url") val downloadUrl: String
)