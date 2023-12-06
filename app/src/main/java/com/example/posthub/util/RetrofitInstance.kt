package com.example.posthub.util

import com.example.posthub.retrofit.PostApi
import com.example.posthub.util.MoshiInstance.moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    val api: PostApi by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://picsum.photos/v2/")
            .build()
            .create(PostApi::class.java)
    }
}
