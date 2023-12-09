package com.example.posthub.core.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.posthub.R
import com.example.posthub.core.ui.fragments.HomeFragmentDirections
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
            val bundle: Bundle = Bundle()
            container.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCreatePostFragment(post)
//                bundle.putParcelable("postArg", post)
                it.findNavController().navigate(action)
            }
//             cardView.setCardBackgroundColor(post.color)
            commentTextView.text = post.comment
            createDate.text = post.date.toString()
        }
    }
}