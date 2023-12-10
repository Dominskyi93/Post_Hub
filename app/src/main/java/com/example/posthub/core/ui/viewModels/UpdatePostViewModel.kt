package com.example.posthub.core.ui.viewModels

import android.app.Application
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posthub.model.entity.Post
import com.example.posthub.retrofit.Photo
import com.example.posthub.util.Colors
import com.example.posthub.util.RetrofitInstance
import com.example.posthub.util.RoomModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class DownloadStatus {
    LOADING,
    DONE,
    ERROR
}

@HiltViewModel
class UpdatePostViewModel @Inject constructor(application: Application) : ViewModel() {
    private val db = RoomModule.provideDatabase(application)

    //    val db = RoomModule.provideDatabase()
    private val _status = MutableLiveData<DownloadStatus>()
    val status: LiveData<DownloadStatus> = _status

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos

    private val selectedColor = MutableLiveData<Colors>()
    private val photoColor = MutableLiveData<Int>()  // Додано для збереження кольору фото
    private val comment = MutableLiveData<String>()  // Додано для збереження коментаря

    fun getSelectedColor(): LiveData<Colors> = selectedColor
    fun getPhotoColor(): LiveData<Int> = photoColor
    fun getComment(): LiveData<String> = comment

    fun setSelectedColor(color: Colors) {
        selectedColor.value = color
    }

    fun setPhotoColor(color: Int) {
        photoColor.value = color
    }

    fun setComment(commentText: String) {
        comment.value = commentText
    }

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
                // Handle the exception
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
                _photos.value = emptyList()  // Замінено на emptyList() для більш читабельності
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
                // Handle the exception
            }
        }
    }

//    private fun showExitConfirmationDialog(post: Post) {
//        val builder = AlertDialog.Builder(requireContext())
//        builder.setTitle("Leave editing?")
//        builder.setMessage("Do you want to save changes before leaving?")
//        builder.setPositiveButton("Save and exit") { _, _ ->
//            saveAndExit(post)
//        }
//        builder.setNegativeButton("Discard changes") { _, _ ->
//            navigateFromUpdateToHome()
//        }
//        builder.setNeutralButton("Continue editing") { dialog, _ ->
//            dialog.dismiss()
//        }
//        builder.create().show()
//    }

}

