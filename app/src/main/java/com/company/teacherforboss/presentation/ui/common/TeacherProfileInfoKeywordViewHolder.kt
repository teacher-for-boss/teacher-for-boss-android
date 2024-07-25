package com.company.teacherforboss.presentation.ui.common

import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.ItemTeacherProfileKeywordBinding

class TeacherProfileInfoKeywordViewHolder(
    private val binding: ItemTeacherProfileKeywordBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: String) {
        binding.keyword = item
    }
}
