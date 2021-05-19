package com.example.risingskills

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.ItemClickListener
import com.example.cookbook.databinding.ItemLayoutBinding

class RecyclerAdapter(
    private var videoTitleList: List<String>,
    var itemClickListener: ItemClickListener
) : RecyclerView.Adapter<RecyclerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        val itemLayoutBinding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(itemLayoutBinding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        val title = videoTitleList[position]
        holder.setData(title, itemClickListener)
    }

    override fun getItemCount(): Int {

        return videoTitleList.size
    }
}

