package com.example.teacherforboss.presentation.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemTeacherKeywordBinding
import com.example.teacherforboss.presentation.type.KeywordType

class HomeWeeklyBestTeacherKeywordViewHolder(
    private val binding: ItemTeacherKeywordBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: KeywordType) {
        binding.keywordType = item
    }
}
