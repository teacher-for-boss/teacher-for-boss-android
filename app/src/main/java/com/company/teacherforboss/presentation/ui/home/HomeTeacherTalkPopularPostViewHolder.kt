package com.company.teacherforboss.presentation.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.ItemHomeTeacherTalkPopularPostBinding
import com.company.teacherforboss.domain.model.home.TeacherTalkPopularPostEntity

class HomeTeacherTalkPopularPostViewHolder(
    private val binding: ItemHomeTeacherTalkPopularPostBinding,
    private val clickItem: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: TeacherTalkPopularPostEntity) {
        with(binding) {
            teacherTalkPopularPostItem = item
            layoutTeacherTalkPopularPost.setOnClickListener { clickItem(item.id) }
        }
    }
}
