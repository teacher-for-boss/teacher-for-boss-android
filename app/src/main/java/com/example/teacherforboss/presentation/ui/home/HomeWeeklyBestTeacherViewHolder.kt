package com.example.teacherforboss.presentation.ui.home

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemHomeWeeklyBestTeacherBinding
import com.example.teacherforboss.domain.model.home.WeeklyBestTeacherEntity

class HomeWeeklyBestTeacherViewHolder(
    private val binding: ItemHomeWeeklyBestTeacherBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private val keywordAdapter: HomeWeeklyBestTeacherKeywordAdapter by lazy { HomeWeeklyBestTeacherKeywordAdapter() }

    init {
        binding.rvWeeklyBestTeacherKeyword.adapter = keywordAdapter
    }

    fun onBind(item: WeeklyBestTeacherEntity) {
        binding.weeklyBestTeacherItem = item
        keywordAdapter.submitList(item.keyword)
    }
}
