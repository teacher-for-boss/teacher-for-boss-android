package com.company.teacherforboss.presentation.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.ItemHomeBossTalkPopularPostBinding
import com.company.teacherforboss.domain.model.home.BossTalkPopularPostEntity

class HomeBossTalkPopularPostViewHolder(
    private val binding: ItemHomeBossTalkPopularPostBinding,
    private val clickItem: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: BossTalkPopularPostEntity) {
        with(binding) {
            bossTalkPopularPostEntity = item
            tvBossTalkPopularPostNumber.text = adapterPosition.inc().toString()
            layoutBossTalkPopularPost.setOnClickListener { clickItem(item.id) }
        }
    }
}
