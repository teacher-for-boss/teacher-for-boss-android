package com.example.teacherforboss.presentation.ui.bosstalkmain.card

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ItemBossTalkCardBinding
import java.time.format.DateTimeFormatter

class BossTalkMainCardAdapter(context: Context) :
    RecyclerView.Adapter<BossTalkMainCardAdapter.BossTalkMainCardViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var bossTalkCardList: MutableList<BossTalkMainCard> = mutableListOf()
    private var allBossTalkMainCard: List<BossTalkMainCard> = emptyList()


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


    fun setCardList(cardList: List<BossTalkMainCard>) {
        this.allBossTalkMainCard = cardList
        this.bossTalkCardList = allBossTalkMainCard.take(10).toMutableList()
        notifyDataSetChanged()
    }

    fun addMoreCards() {
        val currentSize = bossTalkCardList.size
        val nextSize = minOf(currentSize + 10, allBossTalkMainCard.size)
        if (currentSize < nextSize) {
            bossTalkCardList.addAll(allBossTalkMainCard.subList(currentSize, nextSize))
            notifyItemRangeInserted(currentSize, nextSize - currentSize)
        }
    }

    inner class BossTalkMainCardViewHolder(private val binding: ItemBossTalkCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(bossTalkCard: BossTalkMainCard) {
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
            binding.tvBossTalkBookmarkCount.text = bossTalkCard.bookmark_count
            binding.tvBossTalkLikeCount.text = bossTalkCard.like_count
            binding.tvBossTalkCommentCount.text = bossTalkCard.comment_count

            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
            val formattedDate = bossTalkCard.created_at.format(formatter)
            binding.tvBossTalkDate.text = formattedDate

            binding.tvBossTalkBookmarkCount.text = bossTalkCard.bookmark_count
            binding.tvBossTalkLikeCount.text = bossTalkCard.like_count
            binding.tvBossTalkCommentCount.text = bossTalkCard.comment_count

            binding.icBossTalkBookmark.isSelected = bossTalkCard.bookmarked
            binding.tvBossTalkBookmarkCount.isSelected = bossTalkCard.bookmarked

            binding.icBossTalkLike.isSelected = bossTalkCard.liked
            binding.tvBossTalkLikeCount.isSelected = bossTalkCard.liked

        }
    }
}
