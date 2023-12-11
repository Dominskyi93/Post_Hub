package com.example.posthub.repository

import com.example.posthub.retrofit.PhotoService
import javax.inject.Inject

class PhotoRepository
@Inject
constructor(private val api: PhotoService) {
    suspend fun getAllPhotos() = api.getAllPhotos()
}