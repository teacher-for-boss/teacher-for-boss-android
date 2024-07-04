package com.example.teacherforboss.presentation.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemHomeWeeklyBestTeacherBinding
import com.example.teacherforboss.domain.model.home.WeeklyBestTeacherEntity

class HomeWeeklyBestTeacherViewHolder(
    private val binding: ItemHomeWeeklyBestTeacherBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: WeeklyBestTeacherEntity) {
        binding.weeklyBestTeacherItem = item
    }
}
