package com.example.posthub.model

import com.example.posthub.model.entity.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(private val postDao: PostDao) {
    fun getAllPosts(): Flow<List<Post>> {
        return postDao.getAllPosts()
    }

    fun updatePost(post: Post) {
        postDao.updatePost(post)
    }

    fun deletePost(post: Post) {
        postDao.deletePost(post)
    }

    fun createPost(post: Post) {
        postDao.createPost(post)
    }
}