package com.example.teacherforboss.presentation.ui.community.boss_talk.main.card

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemBossTalkCardBinding
import java.time.format.DateTimeFormatter

class BossTalkMainCardViewHolder(private val binding: ItemBossTalkCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(cardData: BossTalkMainCard) {
        with(binding) {
            tvBossTalkTitle.text = "Q. " + cardData.title
            tvBossTalkContent.text = cardData.content
            tvBossTalkBookmarkCount.text = cardData.bookmark_count
            tvBossTalkLikeCount.text = cardData.like_count
            tvBossTalkCommentCount.text = cardData.comment_count

            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
            val formattedDate = cardData.created_at.format(formatter)
            tvBossTalkDate.text = formattedDate
        }
    }
}
