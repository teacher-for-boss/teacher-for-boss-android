package com.example.teacherforboss.presentation.ui.community.boss_talk.main.card

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemBossTalkCardBinding
import com.example.teacherforboss.domain.model.community.BossTalkPostsResponseEntity
import com.example.teacherforboss.domain.model.community.PostEntity

class BossTalkMainCardViewHolder(private val binding: ItemBossTalkCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(cardData: PostEntity) {
        with(binding) {
            tvBossTalkTitle.text = "Q. " + cardData.title
            tvBossTalkContent.text = cardData.content
            tvBossTalkDate.text = cardData.createdAt
            tvBossTalkBookmarkCount.text = cardData.bookmarkCount.toString()
            tvBossTalkLikeCount.text = cardData.likeCount.toString()
            tvBossTalkCommentCount.text = cardData.commentCount.toString()
        }
    }
}
