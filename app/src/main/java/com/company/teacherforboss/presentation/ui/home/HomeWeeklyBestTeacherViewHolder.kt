package com.company.teacherforboss.presentation.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.ItemHomeWeeklyBestTeacherBinding
import com.company.teacherforboss.domain.model.home.WeeklyBestTeacherEntity
import com.company.teacherforboss.util.view.loadCircularImage

class HomeWeeklyBestTeacherViewHolder(
    private val binding: ItemHomeWeeklyBestTeacherBinding,
    private val clickItem: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private val keywordAdapter: HomeWeeklyBestTeacherKeywordAdapter by lazy { HomeWeeklyBestTeacherKeywordAdapter() }

    init {
        binding.rvWeeklyBestTeacherKeyword.adapter = keywordAdapter
    }

    fun onBind(item: WeeklyBestTeacherEntity) {
        with(binding) {
            weeklyBestTeacherItem = item
            ivWeeklyBestTeacherProfile.loadCircularImage(item.profileImg)
            layoutWeeklyBestTeacher.setOnClickListener { clickItem(item.id) }
        }
        keywordAdapter.submitList(item.keyword)
    }
}
