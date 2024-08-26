package com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.RvItemTagWriteBinding
import com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.TeacherTalkAskViewModel

class rvAdapterTagTeacher (
    private val tagList: ArrayList<String>,
    private val deleteTag:(Int)->Unit
): RecyclerView.Adapter<rvAdapterTagTeacher.ViewHolder>() {

    inner class ViewHolder(private val binding: RvItemTagWriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: String) {
            binding.hashtagName.text = tag

            binding.deleteHashtag.setOnClickListener {
                val position = adapterPosition
                deleteTag(position)
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