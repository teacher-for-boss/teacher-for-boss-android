package com.example.teacherforboss.presentation.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.teacherforboss.databinding.ItemHomeWeeklyBestTeacherBinding
import com.example.teacherforboss.domain.model.home.WeeklyBestTeacherEntity

class HomeWeeklyBestTeacherViewHolder(
    private val binding: ItemHomeWeeklyBestTeacherBinding,
    private val context: Context,
) : RecyclerView.ViewHolder(binding.root) {
    private val keywordAdapter: HomeWeeklyBestTeacherKeywordAdapter by lazy { HomeWeeklyBestTeacherKeywordAdapter() }

    init {
        binding.rvWeeklyBestTeacherKeyword.adapter = keywordAdapter
    }

    fun onBind(item: WeeklyBestTeacherEntity) {
        with(binding) {
            weeklyBestTeacherItem = item
            Glide.with(context)
                .load(item.profileImg)
                .centerCrop()
                .circleCrop()
                .into(ivWeeklyBestTeacherProfile)
        }
        keywordAdapter.submitList(item.keyword)
    }
}
