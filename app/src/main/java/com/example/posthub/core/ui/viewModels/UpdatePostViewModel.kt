package com.example.posthub.core.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posthub.model.entity.Post
import com.example.posthub.repository.PhotoRepository
import com.example.posthub.repository.PostRepository
import com.example.posthub.retrofit.Photo
import com.example.posthub.util.DownloadStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UpdatePostViewModel @Inject constructor(
    private val repository: PhotoRepository,
    private val postRepository: PostRepository
) : ViewModel() {
    private val _status = MutableLiveData<DownloadStatus>()
    val status: LiveData<DownloadStatus> = _status
    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos

    init {
        getPhotos()
    }

    private fun getPhotos() = viewModelScope.launch {
        repository.getAllPhotos().let { response ->
            _status.value = DownloadStatus.LOADING
            if (response.isSuccessful) {
                _photos.postValue(response.body())
                _status.value = DownloadStatus.DONE
            } else {
                _status.value = DownloadStatus.ERROR
                _photos.value = emptyList()
            }
        }
    }

    fun updateData(post: Post) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    postRepository.updatePost(post)
                }
            } catch (e: Exception) {
                throw RuntimeException("Cannot update the data")
            }
        }
    }

    fun insertData(post: Post) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    postRepository.createPost(post)
                }
            } catch (e: Exception) {
                throw RuntimeException("Cannot insert the data")
            }
        }
    }
}