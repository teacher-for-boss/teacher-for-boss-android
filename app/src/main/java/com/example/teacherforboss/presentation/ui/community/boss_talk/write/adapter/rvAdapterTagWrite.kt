package com.example.teacherforboss.presentation.ui.community.boss_talk.write.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.RvItemTagWriteBinding
import com.example.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteViewModel

class rvAdapterTagWrite(private val tagList: ArrayList<String>, private val viewModel: BossTalkWriteViewModel): RecyclerView.Adapter<rvAdapterTagWrite.ViewHolder>() {

    inner class ViewHolder(private val binding: RvItemTagWriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: String, viewModel: BossTalkWriteViewModel) {
            binding.hashtagName.text = tag

            binding.deleteHashtag.setOnClickListener {
                val position = adapterPosition
                viewModel.deleteHashTag(position)
                notifyItemRemoved(position)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemTagWriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tagList[position], viewModel = viewModel)
    }

    override fun getItemCount(): Int {
        return tagList.size
    }
}