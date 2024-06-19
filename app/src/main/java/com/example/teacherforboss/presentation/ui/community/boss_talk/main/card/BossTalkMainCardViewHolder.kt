package com.example.teacherforboss.presentation.ui.community.boss_talk.main.card

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemBossTalkCardBinding
import com.example.teacherforboss.R
import com.example.teacherforboss.domain.model.community.PostEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BossTalkMainCardViewHolder(
    private val binding: ItemBossTalkCardBinding,
    private val patchOnClick: (Long) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(cardData: PostEntity) {
        with(binding) {
            tvBossTalkTitle.text = "Q. " + cardData.title
            tvBossTalkContent.text = cardData.content
            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
            val formattedDate = cardData.createdAt.format(formatter)
            tvBossTalkDate.text = formattedDate
            tvBossTalkBookmarkCount.text = cardData.bookmarkCount.toString()
            tvBossTalkLikeCount.text = cardData.likeCount.toString()
            tvBossTalkCommentCount.text = cardData.commentCount.toString()

            root.setOnClickListener{patchOnClick(cardData.postId)}
        }
        if(cardData.like) binding.icBossTalkLike.setImageResource(R.drawable.ic_like_on)
        if(cardData.bookmark) binding.icBossTalkBookmark.setImageResource(R.drawable.ic_bookmark_on)
    }

    fun extractDate(text:String):String{
        val pattern="\\d{4}-\\d{2}-\\d{2}".toRegex()
        val match=pattern.find(text)
        return match?.value?:""
    }
}
