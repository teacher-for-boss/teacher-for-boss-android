package com.example.teacherforboss.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.teacherforboss.databinding.ItemHomeBossTalkPopularPostBinding
import com.example.teacherforboss.domain.model.home.BossTalkPopularPostEntity
import com.example.teacherforboss.util.view.ItemDiffCallback

class HomeBossTalkPopularPostAdapter() :
    ListAdapter<BossTalkPopularPostEntity, HomeBossTalkPopularPostViewHolder>(
        ItemDiffCallback<BossTalkPopularPostEntity>(
            onItemsTheSame = { old, new -> old.number == new.number },
            onContentsTheSame = { old, new -> old == new },
        ),
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeBossTalkPopularPostViewHolder = HomeBossTalkPopularPostViewHolder(
        ItemHomeBossTalkPopularPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    )

    override fun onBindViewHolder(holder: HomeBossTalkPopularPostViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
