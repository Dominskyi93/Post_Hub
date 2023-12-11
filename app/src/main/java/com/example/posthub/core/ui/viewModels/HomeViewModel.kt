package com.example.posthub.core.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posthub.model.entity.Post
import com.example.posthub.model.room.PostDatabase
import com.example.posthub.util.DownloadStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(database: PostDatabase) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts
    private val _status = MutableLiveData<DownloadStatus>()
    val status: LiveData<DownloadStatus> = _status
    private val postDao = database.getDao()

    init {
        getAllPosts()
    }

    private fun getAllPosts() {
        viewModelScope.launch {
            _status.value = DownloadStatus.LOADING
            try {
                postDao.getAllPosts().collect { posts ->
                    _posts.value = posts
                    _status.value = DownloadStatus.DONE
                }
            } catch (e: Exception) {
                _status.value = DownloadStatus.ERROR
                _posts.value = listOf()
            }
        }
    }
}