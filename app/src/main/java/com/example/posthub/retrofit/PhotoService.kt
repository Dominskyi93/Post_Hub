package com.example.posthub.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface PhotoService {
    @GET("list")
    suspend fun getAllPhotos(): Response<List<Photo>>
}