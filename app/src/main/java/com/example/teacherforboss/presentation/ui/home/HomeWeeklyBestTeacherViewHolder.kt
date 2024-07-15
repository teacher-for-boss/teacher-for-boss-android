package com.example.teacherforboss.presentation.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.teacherforboss.databinding.ItemHomeWeeklyBestTeacherBinding
import com.example.teacherforboss.domain.model.home.WeeklyBestTeacherEntity
import com.example.teacherforboss.util.view.loadCircularImage

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
            ivWeeklyBestTeacherProfile.loadCircularImage(item.profileImg)
        }
        keywordAdapter.submitList(item.keyword)
    }
}
