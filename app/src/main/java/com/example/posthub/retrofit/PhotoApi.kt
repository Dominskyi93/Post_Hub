package com.example.posthub.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface PhotoApi {
    @GET("list")
    suspend fun getPosts(): Response<List<Photo>>
}