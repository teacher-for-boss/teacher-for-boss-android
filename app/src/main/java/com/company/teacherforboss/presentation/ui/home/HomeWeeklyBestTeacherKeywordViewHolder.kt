package com.company.teacherforboss.presentation.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.ItemTeacherKeywordBinding

class HomeWeeklyBestTeacherKeywordViewHolder(
    private val binding: ItemTeacherKeywordBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: String) {
        binding.keyword = item
    }
}
