package com.example.posthub.core.ui.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posthub.model.entity.Post
import com.example.posthub.util.RoomModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts
    private val db = RoomModule.provideDatabase(application)

    init {
        getAllPosts()
    }

    private fun getAllPosts() {
        viewModelScope.launch {
            try {
                db.getDao().getAllPosts().collect { posts ->
                    _posts.value = posts
                }
            } catch (e: Exception) {
                _posts.value = listOf()
            }
        }
    }
}
