package com.example.posthub.retrofit

import retrofit2.http.GET

interface PostApi {
    @GET("list?limit=5")
    suspend fun getPosts(): List<Post>
}