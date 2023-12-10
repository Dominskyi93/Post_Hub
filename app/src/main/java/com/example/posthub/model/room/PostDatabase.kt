package com.example.posthub.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.posthub.model.PostDao
import com.example.posthub.model.entity.Post

@Database(
    version = 1,
    entities = [Post::class]
)

@TypeConverters(Converter::class)
abstract class PostDatabase : RoomDatabase() {
    abstract fun getDao(): PostDao

}