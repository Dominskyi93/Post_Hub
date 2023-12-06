package com.example.posthub.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {
    @GET("list")
    suspend fun getPosts(): Response<List<Photo>>
}