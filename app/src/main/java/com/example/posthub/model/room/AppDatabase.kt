package com.example.posthub.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.posthub.model.PostsDao
import com.example.posthub.model.entity.Post

@Database(
    version = 1,
    entities = [Post::class]
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): PostsDao

    companion object {
        fun getDataBase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "posthub.db"
            ).build()
        }
    }
}