package com.example.weather.util

import com.example.posthub.retrofit.PostApi
import com.example.weather.util.MoshiInstance.moshi
import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
//    private val interceptor = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//    }
//
//    private val client = OkHttpClient.Builder()
//        .addInterceptor(interceptor)
//        .build()

    val api by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://picsum.photos/v2/")
//            .client(client)
            .build()
            .create(PostApi::class.java)
    }
}
