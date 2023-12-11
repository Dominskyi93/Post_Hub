package com.example.posthub.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.posthub.model.room.PostDatabase
import com.example.posthub.retrofit.PhotoService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://picsum.photos/v2/"
    private const val DATABASE_NAME = "posthub.db"

    @Provides
    fun provideRetrofitInstance(): PhotoService {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(getMoshiInstance()))
            .baseUrl(BASE_URL)
            .build()
            .create(PhotoService::class.java)
    }

    @Provides
    fun getMoshiInstance(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PostDatabase {
        return Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    fun provideAppContext(@ApplicationContext application: Application): Context {
        return application.applicationContext
    }
}