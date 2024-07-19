package com.example.teacherforboss.presentation.ui.community.teacher_talk.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.RvItemTeacherSearchCardBinding

class rvAdapterCardTeacher(private val CardList: List<String>
): RecyclerView.Adapter<rvAdapterCardTeacher.ViewHolder>() {
    class ViewHolder(private val binding: RvItemTeacherSearchCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(card: String) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemTeacherSearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return CardList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(card = CardList[position])
    }
}