package com.example.posthub.core.ui

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.posthub.R
import com.example.posthub.core.ui.adapters.PhotoAdapter
import com.example.posthub.core.ui.adapters.PostAdapter
import com.example.posthub.model.entity.Post
import com.example.posthub.retrofit.Photo
import com.example.posthub.util.DownloadStatus

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_img)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<Photo>?
) {
    val adapter = recyclerView.adapter as PhotoAdapter
    adapter.submitList(data)
}

@BindingAdapter("listData")
fun bindRecyclerViewPost(recyclerView: RecyclerView, data: LiveData<List<Post>>?) {
    val adapter = recyclerView.adapter as? PostAdapter
    adapter?.submitList(data?.value)
}

@BindingAdapter("downloadStatus")
fun bindStatus(
    statusImageView: ImageView,
    status: DownloadStatus?
) {
    when (status) {
        DownloadStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }

        DownloadStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }

        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}