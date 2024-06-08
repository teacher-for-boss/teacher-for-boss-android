package com.example.teacherforboss.presentation.ui.teachertalkmain.Category

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemTeacherTalkCategoryBinding

class TeacherTalkCategoryViewHolder(private val binding: ItemTeacherTalkCategoryBinding) :
RecyclerView.ViewHolder(binding.root) {

    fun onBind(categoryData: TeacherTalkCategory) {
            binding.tvTeacherTalkCategory.text= categoryData.category_name
    }
}
