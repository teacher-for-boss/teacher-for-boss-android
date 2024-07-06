package com.example.teacherforboss.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.teacherforboss.databinding.ItemHomeWeeklyBestTeacherBinding
import com.example.teacherforboss.domain.model.home.WeeklyBestTeacherEntity
import com.example.teacherforboss.util.view.ItemDiffCallback

class HomeWeeklyBestTeacherAdapter(private val context: Context) :
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
        context,
    )

    override fun onBindViewHolder(holder: HomeWeeklyBestTeacherViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
