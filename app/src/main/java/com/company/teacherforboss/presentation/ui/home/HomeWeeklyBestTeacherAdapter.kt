package com.company.teacherforboss.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.company.teacherforboss.databinding.ItemHomeWeeklyBestTeacherBinding
import com.company.teacherforboss.domain.model.home.WeeklyBestTeacherEntity
import com.company.teacherforboss.util.view.ItemDiffCallback

class HomeWeeklyBestTeacherAdapter(
    private val clickItem: (Long) -> Unit,
) :
    ListAdapter<WeeklyBestTeacherEntity, HomeWeeklyBestTeacherViewHolder>(
        ItemDiffCallback<WeeklyBestTeacherEntity>(
            onItemsTheSame = { old, new -> old.nickName == new.nickName },
            onContentsTheSame = { old, new -> old == new },
        ),
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeWeeklyBestTeacherViewHolder = HomeWeeklyBestTeacherViewHolder(
        ItemHomeWeeklyBestTeacherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
        clickItem
    )

    override fun onBindViewHolder(holder: HomeWeeklyBestTeacherViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
