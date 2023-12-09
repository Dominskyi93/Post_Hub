package com.example.posthub.core.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.posthub.R
import com.example.posthub.databinding.ColorItemBinding
import com.example.posthub.util.Colors

class ColorAdapter(private val colors: List<Colors>) :
    RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {
    class ColorViewHolder(
        binding: ColorItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val card = binding.cvColorItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(ColorItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val red = R.color.cherry

        val colorValue: Int = ContextCompat.getColor(holder.itemView.context, colors[position].colorValue)
//            colors[position].colorValue
        holder.card.setCardBackgroundColor(colorValue)
    }
}