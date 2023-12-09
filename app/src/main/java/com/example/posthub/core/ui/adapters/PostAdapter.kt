package com.example.posthub.core.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.posthub.R
import com.example.posthub.databinding.PostItemBinding
import com.example.posthub.model.entity.Post

class PostAdapter :
    ListAdapter<Post, PostAdapter.PostViewHolder>(DiffCallback) {
    class PostViewHolder(private var binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val commentTextView = binding.tvComment
        val createDate = binding.tvDate
        val container = binding.clContainer
        var cardView = binding.cvCardItem
        fun bind(post: Post) {
            binding.post = post
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.photo == newItem.photo
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(PostItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        with(holder) {
            bind(post)
            container.setOnClickListener {
                it.findNavController().navigate(R.id.action_homeFragment_to_createPostFragment)
            }
//             cardView.setCardBackgroundColor(post.color)
            commentTextView.text = post.comment
            createDate.text = post.date.toString()
        }
    }
}