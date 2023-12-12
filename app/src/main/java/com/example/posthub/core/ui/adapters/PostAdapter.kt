package com.example.posthub.core.ui.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.posthub.R
import com.example.posthub.core.ui.fragments.HomeFragmentDirections
import com.example.posthub.databinding.PostItemBinding
import com.example.posthub.model.entity.Post
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val DATE_PATTERN = "d MMM"
private const val EMPTY_STRING = ""

class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(DiffCallback) {

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
                val action = HomeFragmentDirections.actionHomeFragmentToCreatePostFragment(post)
                it.findNavController().navigate(action)
            }
            val color = try {
                ContextCompat.getColor(itemView.context, post.color)
            } catch (e: Resources.NotFoundException) {
                ContextCompat.getColor(itemView.context, R.color.black)
            }
            cardView.setCardBackgroundColor(color)
            commentTextView.text = post.comment
            val createDate = formatLocalDate(post.createDate)
            val editDate = formatLocalDate(post.editDate)
            this.createDate.text = if (post.editDate == null) createDate else "Edit: $editDate"
        }
    }

    private fun formatLocalDate(localDate: LocalDate?): String {
        if (localDate == null) return EMPTY_STRING
        val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH)
        return localDate.format(formatter)
    }
}