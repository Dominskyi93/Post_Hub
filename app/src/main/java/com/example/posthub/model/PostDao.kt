package com.example.posthub.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.posthub.model.entity.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY createDate DESC")
    fun getAllPosts(): Flow<List<Post>>

    @Update
    fun updatePost(post: Post)

    @Delete
    fun deletePost(post: Post)

    @Insert
    fun createPost(post: Post)
}
