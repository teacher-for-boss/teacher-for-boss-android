package com.example.teacherforboss.presentation.ui.community.bosstalk_main.card

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemBossTalkCardBinding

class BossTalkMainCardViewHolder(private val binding: ItemBossTalkCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(cardData: BossTalkMainCard) {
        with(binding) {
            tvBossTalkTitle.text = "Q. " + cardData.question
            tvBossTalkContent.text = cardData.answer
            tvBossTalkDate.text = cardData.date
            tvBossTalkBookmarkCount.text = cardData.count_bookmark
            tvBossTalkLikeCount.text = cardData.count_like
            tvBossTalkCommentCount.text = cardData.count_comment
        }
    }
}
