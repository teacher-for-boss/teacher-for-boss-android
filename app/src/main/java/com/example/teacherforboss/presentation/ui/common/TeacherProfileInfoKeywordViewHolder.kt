package com.example.teacherforboss.presentation.ui.common

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemTeacherProfileKeywordBinding

class TeacherProfileInfoKeywordViewHolder(
    private val binding: ItemTeacherProfileKeywordBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: String) {
        binding.keyword = item
    }
}
