package com.example.teacherforboss.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.teacherforboss.databinding.ItemHomeTeacherTalkPopularPostBinding
import com.example.teacherforboss.domain.model.home.TeacherTalkPopularPostEntity
import com.example.teacherforboss.util.view.ItemDiffCallback

class HomeTeacherTalkPopularPostAdapter() :
    ListAdapter<TeacherTalkPopularPostEntity, HomeTeacherTalkPopularPostViewHolder>(
        ItemDiffCallback<TeacherTalkPopularPostEntity>(
            onItemsTheSame = { old, new -> old.content == new.content },
            onContentsTheSame = { old, new -> old == new },
        ),
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeTeacherTalkPopularPostViewHolder = HomeTeacherTalkPopularPostViewHolder(
        ItemHomeTeacherTalkPopularPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    )

    override fun onBindViewHolder(holder: HomeTeacherTalkPopularPostViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
