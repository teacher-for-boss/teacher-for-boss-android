package com.company.teacherforboss.presentation.ui.community.boss_talk.main.card

import android.content.Context
import android.content.Intent
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ItemBossTalkCardBinding
import com.company.teacherforboss.domain.model.community.boss.PostEntity
import com.company.teacherforboss.util.base.LocalDateFormatter

class BossTalkMainCardAdapter(
    private val clickItem: (Long) -> Unit
) :
    RecyclerView.Adapter<BossTalkMainCardAdapter.BossTalkMainCardViewHolder>() {

    private var bossTalkCardList: MutableList<PostEntity> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BossTalkMainCardViewHolder =BossTalkMainCardViewHolder(
        ItemBossTalkCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: BossTalkMainCardViewHolder, position: Int) {
        holder.onBind(bossTalkCardList[position])
    }

    override fun getItemCount(): Int = bossTalkCardList.size


    fun setCardList(cardList: List<PostEntity>) {
        this.bossTalkCardList = cardList.take(10).toMutableList()
        notifyDataSetChanged()
    }

    fun addMoreCards(newPostList:List<PostEntity>) {
        val currentSize = bossTalkCardList.size
        val newItemSize=newPostList.size
        if(newItemSize>0){
            bossTalkCardList.addAll(newPostList)
            notifyItemRangeInserted(currentSize,newItemSize)
        }
    }

    inner class BossTalkMainCardViewHolder(
        private val binding: ItemBossTalkCardBinding,
        ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(bossTalkCard: PostEntity) {
            val questionText = "Q. ${bossTalkCard.title}"

            // Q. 부분 색상 설정
            val spannable = SpannableString(questionText)
            val purpleColor = ContextCompat.getColor(binding.root.context, R.color.Purple600)
            val grayColor = ContextCompat.getColor(binding.root.context, R.color.Gray700)

            val colorSpanQ = ForegroundColorSpan(purpleColor)
            val colorSpanRest = ForegroundColorSpan(grayColor)

            spannable.setSpan(colorSpanQ, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(colorSpanRest, 2, questionText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            with(binding){
                tvBossTalkTitle.text = spannable
                tvBossTalkContent.text = bossTalkCard.content
                tvBossTalkBookmarkCount.text = bossTalkCard.bookmarkCount.toString()
                tvBossTalkLikeCount.text = bossTalkCard.likeCount.toString()
                tvBossTalkCommentCount.text = bossTalkCard.commentCount.toString()
                tvBossTalkDate.text = LocalDateFormatter.extractDate(bossTalkCard.createdAt)

                icBossTalkBookmark.isSelected = bossTalkCard.bookmarked
                tvBossTalkBookmarkCount.isSelected = bossTalkCard.bookmarked

                icBossTalkLike.isSelected = bossTalkCard.liked
                tvBossTalkLikeCount.isSelected = bossTalkCard.liked

                root.setOnClickListener{
                    clickItem(bossTalkCard.postId)
                }

            }

        }
    }

}
