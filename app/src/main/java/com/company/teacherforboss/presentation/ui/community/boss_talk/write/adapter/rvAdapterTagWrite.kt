package com.company.teacherforboss.presentation.ui.community.boss_talk.write.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.RvItemTagWriteBinding
import com.company.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteViewModel

class rvAdapterTagWrite(
    private val tagList: ArrayList<String>,
    private val clickDeleteTag:(Int)->Unit
): RecyclerView.Adapter<rvAdapterTagWrite.ViewHolder>() {

    inner class ViewHolder(
        private val binding: RvItemTagWriteBinding
        ): RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: String) {
            binding.hashtagName.text = tag

            binding.root.setOnClickListener{
                val position = adapterPosition
                clickDeleteTag(position)
                notifyItemRemoved(position)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemTagWriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tagList[position])
    }

    override fun getItemCount(): Int {
        return tagList.size
    }
}