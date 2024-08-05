package com.company.teacherforboss.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.company.teacherforboss.databinding.ItemHomeTeacherTalkPopularPostBinding
import com.company.teacherforboss.domain.model.home.TeacherTalkPopularPostEntity
import com.company.teacherforboss.util.view.ItemDiffCallback

class HomeTeacherTalkPopularPostAdapter(
    private val clickItem: (Long) -> Unit
) : ListAdapter<TeacherTalkPopularPostEntity, HomeTeacherTalkPopularPostViewHolder>(
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
        clickItem
    )

    override fun onBindViewHolder(holder: HomeTeacherTalkPopularPostViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
