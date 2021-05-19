package com.example.risingskills

import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.ItemClickListener
import com.example.cookbook.databinding.ItemLayoutBinding

class RecyclerViewHolder(private val view: ItemLayoutBinding) : RecyclerView.ViewHolder(view.root) {

    fun setData(title: String, itemClickListenerVideoTitle: ItemClickListener) {

        view.apply {

            view.tvTitle.text = title
            //view.ivCusineImage.visibility = View.GONE
            //view.tvDescription.visibility = View.GONE

            view.RecyclerItem.setOnClickListener {
                itemClickListenerVideoTitle.onItemClicked(position = adapterPosition, cuisine = title)
            }
        }

    }

}
