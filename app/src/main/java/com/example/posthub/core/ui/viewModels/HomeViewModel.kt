package com.example.posthub.core.ui.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posthub.R
import com.example.posthub.model.entity.Post
import com.example.posthub.model.room.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

enum class ApiStatus { LOADING, ERROR, DONE }
@HiltViewModel
class HomeViewModel  @Inject constructor(private val application: Application) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts
    private val db = AppDatabase.getDataBase(application)

    init {
        getAllPosts()
    }

    private fun getAllPosts() {
//        var ppost = Post(null, "https://picsum.photos/id/4/5000/3333", "comma", LocalDate.now(), R.color.red)
//        CoroutineScope(Dispatchers.IO).launch {
//            db.getDao().createPost(ppost)
//        }



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
