package com.example.posthub.core.di

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AuthModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @ApplicationContext
    fun provideAppContext(application: Application): Context {
        return application.applicationContext
    }

//    @Provides
//    @Singleton
//    @ApplicationContext
//    fun getDataBaseInstance(application: Application): AppDatabase {
//        return AppDatabase.getDataBase(application)
//    }
}