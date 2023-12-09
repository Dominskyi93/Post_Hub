package com.example.posthub.model.room

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.posthub.core.di.AppComponent
import com.example.posthub.model.PostsDao
import com.example.posthub.model.entity.Post
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(
    version = 1,
    entities = [Post::class]
)

@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): PostsDao

    companion object {
        fun getDataBase( application: Application): AppDatabase {
            return Room.databaseBuilder(
                application.applicationContext,
                AppDatabase::class.java,
                "posthub.db"
            ).build()
        }
    }
}