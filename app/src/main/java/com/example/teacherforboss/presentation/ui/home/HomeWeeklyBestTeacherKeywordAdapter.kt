package com.example.teacherforboss.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.teacherforboss.databinding.ItemTeacherKeywordBinding
import com.example.teacherforboss.util.view.ItemDiffCallback

class HomeWeeklyBestTeacherKeywordAdapter() :
    ListAdapter<String, HomeWeeklyBestTeacherKeywordViewHolder>(
        ItemDiffCallback<String>(
            onItemsTheSame = { old, new -> old == new },
            onContentsTheSame = { old, new -> old == new },
        ),
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeWeeklyBestTeacherKeywordViewHolder = HomeWeeklyBestTeacherKeywordViewHolder(
        ItemTeacherKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )

    override fun onBindViewHolder(holder: HomeWeeklyBestTeacherKeywordViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
