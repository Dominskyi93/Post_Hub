package com.example.posthub.util

import com.example.posthub.retrofit.PhotoApi
import com.example.posthub.util.MoshiInstance.moshi
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {
    private const val BASE_URL = "https://picsum.photos/v2/"
    val api: PhotoApi by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
            .create(PhotoApi::class.java)
    }
}
