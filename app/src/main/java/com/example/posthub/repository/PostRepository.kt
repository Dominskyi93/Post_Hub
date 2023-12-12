package com.example.posthub.repository

import com.example.posthub.model.entity.Post
import com.example.posthub.model.room.PostDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(database: PostDatabase) {
    private val dao = database.getDao()
    fun getAllPosts(): Flow<List<Post>> {
        return dao.getAllPosts()
    }

    fun updatePost(post: Post) {
        dao.updatePost(post)
    }

    fun deletePost(post: Post) {
        dao.deletePost(post)
    }

    fun createPost(post: Post) {
        dao.createPost(post)
    }
}