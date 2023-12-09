package com.example.posthub.core.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.posthub.databinding.PhotoItemBinding
import com.example.posthub.retrofit.Photo

class PhotoAdapter : ListAdapter<Photo, PhotoAdapter.PhotoViewHolder>(DiffCallback) {

    private var selectedPosition = RecyclerView.NO_POSITION
    private var listener: OnItemClickListener? = null

    class PhotoViewHolder(private var binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.photo = photo
            binding.executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(photo: Photo)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.url == newItem.url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(PhotoItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) = with(holder) {
        val photo = getItem(position)
        bind(photo)
        itemView.isSelected = selectedPosition == adapterPosition
        itemView.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = adapterPosition
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)
            listener?.onItemClick(photo)
        }
    }
}
