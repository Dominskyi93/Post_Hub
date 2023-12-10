package com.example.posthub.core.ui.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posthub.model.entity.Post
import com.example.posthub.retrofit.Photo
import com.example.posthub.util.RetrofitInstance
import com.example.posthub.util.RoomModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import javax.inject.Inject

enum class DownloadStatus {
    LOADING,
    DONE,
    ERROR
}

@HiltViewModel
class UpdatePostViewModel @Inject constructor(application: Application) : ViewModel() {
    private val db = RoomModule.provideDatabase(application)
    private val _status = MutableLiveData<DownloadStatus>()
    val status: LiveData<DownloadStatus> = _status
    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos

    init {
        getPhotos()
    }

    fun updateData(post: Post) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    db.getDao().updatePost(post)
                }
            } catch (e: Exception) {
                _status.value = DownloadStatus.ERROR
            }
        }
    }

    private fun getPhotos() {
        viewModelScope.launch {
            _status.value = DownloadStatus.LOADING
            try {
                _photos.value = RetrofitInstance.api.getPosts().body()
                _status.value = DownloadStatus.DONE
            } catch (e: Exception) {
                _status.value = DownloadStatus.ERROR
                _photos.value = emptyList()
            }
        }
    }

    fun insertData(post: Post) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    db.getDao().createPost(post)
                }
            } catch (e: Exception) {
                throw RuntimeException("Cannot insert the data")
            }
        }
    }
}
