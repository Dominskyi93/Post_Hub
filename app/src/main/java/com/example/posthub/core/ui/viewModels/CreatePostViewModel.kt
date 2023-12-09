package com.example.posthub.core.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posthub.retrofit.Photo
import com.example.posthub.util.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class DownloadStatus { LOADING, ERROR, DONE }

@HiltViewModel
class CreatePostViewModel @Inject constructor() : ViewModel() {

    private val _status = MutableLiveData<DownloadStatus>()
    val status: LiveData<DownloadStatus> = _status

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos

    private val _selectedPhoto = MutableLiveData<String>()
    val selectedPhoto: LiveData<String> get() = _selectedPhoto

    private val _selectedColor = MutableLiveData<Int>()
    val selectedColor: LiveData<Int> get() = _selectedColor

    private val _comment = MutableLiveData<String>()
    val comment: LiveData<String> get() = _comment

    init {
        getPhotos()
    }

    fun selectPhoto(photoPath: String) {
        _selectedPhoto.value = photoPath
    }

    fun selectColor(color: Int) {
        _selectedColor.value = color
    }

    fun setComment(commentText: String) {
        _comment.value = commentText
    }
    fun saveDataToDatabase() {
        // Реалізуйте код для збереження даних в базу даних тут
        // Використовуйте значення _selectedPhoto, _selectedColor, _comment
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