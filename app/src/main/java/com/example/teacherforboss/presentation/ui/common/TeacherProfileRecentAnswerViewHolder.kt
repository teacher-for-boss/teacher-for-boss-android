package com.example.teacherforboss.presentation.ui.common

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemTeacherProfileRecentAnswerBinding
import com.example.teacherforboss.domain.model.common.TeacherRecentAnswerListEntity.TeacherRecentAnswer

class TeacherProfileRecentAnswerViewHolder(
    private val binding: ItemTeacherProfileRecentAnswerBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: TeacherRecentAnswer) {
        with(binding) {
            teacherRecentAnswer = item
        }
    }
}