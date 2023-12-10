package com.example.posthub.util

import android.content.Context
import androidx.room.Room
import com.example.posthub.model.PostDao
import com.example.posthub.model.room.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PostDatabase {
        return Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            "posthub.db"
        ).build()
    }

    @Provides
    fun provideDao(postDatabase: PostDatabase): PostDao {
        return postDatabase.getDao()
    }
}
