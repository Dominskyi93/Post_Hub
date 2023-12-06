package com.example.posthub.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posthub.R
import com.example.posthub.databinding.PostItemBinding
import com.example.posthub.retrofit.Photo

class PostAdapter(private val photoList: List<Photo>) :
    RecyclerView.Adapter<PostAdapter.PostHolder>() {
    class PostHolder(binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val photoImageView = binding.ivPhoto
        val commentTextView = binding.tvComment
        val createDate = binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        return PostHolder(PostItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) = with(holder) {
        photoImageView.setImageResource(R.drawable.eye)
        commentTextView.text = photoList[position].author
        createDate.text = "Sunday"
    }

}