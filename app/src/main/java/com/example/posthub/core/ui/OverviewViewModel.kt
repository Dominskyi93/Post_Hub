package com.example.posthub.core.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posthub.retrofit.Photo
import com.example.posthub.util.RetrofitInstance
import kotlinx.coroutines.launch

enum class DownloadStatus { LOADING, ERROR, DONE }
class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<DownloadStatus>()
    val status: LiveData<DownloadStatus> = _status

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos

    init {
        getPhotos()
    }

    private fun getPhotos() {
        viewModelScope.launch {
            _status.value = DownloadStatus.LOADING
            try {
                _photos.value = RetrofitInstance.api.getPosts().body()
                _status.value = DownloadStatus.DONE
            } catch (e: Exception) {
                _status.value = DownloadStatus.ERROR
                _photos.value = listOf()
            }
        }
    }
}