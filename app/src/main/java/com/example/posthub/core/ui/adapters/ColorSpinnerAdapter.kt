package com.example.posthub.core.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.example.posthub.R
import com.example.posthub.util.Colors

class ColorSpinnerAdapter(private val context: Context, private val items: List<Colors>) :
    BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        val colorView: View = view.findViewById(R.id.colorView)
        val item = getItem(position) as Colors
        val color = ContextCompat.getColor(context, item.colorValue)
        colorView.setBackgroundColor(color)
        return view
    }
}
