package com.example.teacherforboss.presentation.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemHomeBossTalkPopularPostBinding
import com.example.teacherforboss.domain.model.home.BossTalkPopularPostEntity

class HomeBossTalkPopularPostViewHolder(
    private val binding: ItemHomeBossTalkPopularPostBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: BossTalkPopularPostEntity) {
        with(binding) {
            bossTalkPopularPostEntity = item
            tvBossTalkPopularPostNumber.text = adapterPosition.inc().toString()
        }
    }
}
