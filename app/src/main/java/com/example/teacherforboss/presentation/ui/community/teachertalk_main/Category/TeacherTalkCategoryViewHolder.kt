package com.example.teacherforboss.presentation.ui.community.teachertalk_main.Category

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemTeacherTalkCategoryBinding

class TeacherTalkCategoryViewHolder(private val binding: ItemTeacherTalkCategoryBinding) :
RecyclerView.ViewHolder(binding.root) {

    fun onBind(categoryData: TeacherTalkCategory) {
            binding.teacherTalkCategory.text= categoryData.category_name
    }
}
