package com.example.teacherforboss.presentation.ui.community.boss_talk.main.card

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemBossTalkCardBinding
import java.time.format.DateTimeFormatter
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ItemBossTalkCardBinding
import com.example.teacherforboss.domain.model.community.BossTalkPostsResponseEntity
import com.example.teacherforboss.domain.model.community.PostEntity

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
        
        // 혜원
//     fun onBind(cardData: PostEntity) {
//         with(binding) {
//             tvBossTalkTitle.text = "Q. " + cardData.title
//             tvBossTalkContent.text = cardData.content
//             tvBossTalkDate.text = extractDate(cardData.createdAt)
//             tvBossTalkBookmarkCount.text = cardData.bookmarkCount.toString()
//             tvBossTalkLikeCount.text = cardData.likeCount.toString()
//             tvBossTalkCommentCount.text = cardData.commentCount.toString()
//         }
//         if(cardData.like) binding.icBossTalkLike.setImageResource(R.drawable.ic_like_on)
//         if(cardData.bookmark) binding.icBossTalkBookmark.setImageResource(R.drawable.ic_bookmark_on)
//     }
    
//     fun extractDate(text:String):String{
//         val pattern="\\d{4}-\\d{2}-\\d{2}".toRegex()
//         val match=pattern.find(text)
//         return match?.value?:""
//     }
}
