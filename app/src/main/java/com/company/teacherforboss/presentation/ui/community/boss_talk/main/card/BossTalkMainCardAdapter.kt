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
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyActivity
import com.company.teacherforboss.util.base.LocalDateFormatter

class BossTalkMainCardAdapter(context: Context) :
    RecyclerView.Adapter<BossTalkMainCardAdapter.BossTalkMainCardViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var bossTalkCardList: MutableList<PostEntity> = mutableListOf()
    private var allBossTalkMainCard: List<PostEntity> = emptyList()
    private val context=context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BossTalkMainCardViewHolder {
        val binding = ItemBossTalkCardBinding.inflate(inflater, parent, false)
        return BossTalkMainCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BossTalkMainCardViewHolder, position: Int) {
        holder.onBind(bossTalkCardList[position])
    }

    override fun getItemCount(): Int = bossTalkCardList.size


    fun setCardList(cardList: List<PostEntity>) {
        this.allBossTalkMainCard = cardList
        this.bossTalkCardList = allBossTalkMainCard.take(10).toMutableList()
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

    inner class BossTalkMainCardViewHolder(private val binding: ItemBossTalkCardBinding) :
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

            binding.tvBossTalkTitle.text = spannable
            binding.tvBossTalkContent.text = bossTalkCard.content
            binding.tvBossTalkBookmarkCount.text = bossTalkCard.bookmarkCount.toString()
            binding.tvBossTalkLikeCount.text = bossTalkCard.likeCount.toString()
            binding.tvBossTalkCommentCount.text = bossTalkCard.commentCount.toString()
            binding.tvBossTalkDate.text = LocalDateFormatter.extractDate(bossTalkCard.createdAt)

            binding.icBossTalkBookmark.isSelected = bossTalkCard.bookmarked
            binding.tvBossTalkBookmarkCount.isSelected = bossTalkCard.bookmarked

            binding.icBossTalkLike.isSelected = bossTalkCard.liked
            binding.tvBossTalkLikeCount.isSelected = bossTalkCard.liked

            // 상세 글 이동
            binding.root.setOnClickListener {
                val intent=Intent(context,BossTalkBodyActivity::class.java).apply{
                    putExtra("postId",bossTalkCard.postId.toString())
                }
                context.startActivity(intent)
            }

        }
    }

}
