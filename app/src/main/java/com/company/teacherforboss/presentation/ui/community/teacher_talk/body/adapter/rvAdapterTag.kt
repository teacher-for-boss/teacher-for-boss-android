package com.company.teacherforboss.presentation.ui.community.teacher_talk.body.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.RvItemTagBinding

class rvAdapterTag(private val tagList: List<String>): RecyclerView.Adapter<rvAdapterTag.ViewHolder>() {

    class ViewHolder(binding: RvItemTagBinding):RecyclerView.ViewHolder(binding.root) {
        val tag: TextView = binding.tagName
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tag.text = tagList[position]
    }

    override fun getItemCount(): Int {
        return tagList.size
    }
}