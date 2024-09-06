package com.company.teacherforboss.presentation.ui.mypage.saved

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
import com.company.teacherforboss.databinding.RvItemSavedBossBinding
import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsEntity
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyActivity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_POSTID
import com.company.teacherforboss.util.base.LocalDateFormatter

class SavedBossTalkCardAdapter(private val context: Context) :
    RecyclerView.Adapter<SavedBossTalkCardAdapter.SavedBossTalkCardViewHolder>() {

    private val inflater by lazy { LayoutInflater.from(context) }
    private var bookmarkedPostsList: MutableList<BookmarkedPostsEntity> = mutableListOf()
    private var allBookmarkedPosts: List<BookmarkedPostsEntity> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedBossTalkCardViewHolder {
        val binding = RvItemSavedBossBinding.inflate(inflater, parent, false)
        return SavedBossTalkCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedBossTalkCardViewHolder, position: Int) {
        holder.onBind(bookmarkedPostsList[position])
    }

    override fun getItemCount(): Int {
        return bookmarkedPostsList.size
    }


    fun setCardList(cardList: List<BookmarkedPostsEntity>) {
        this.allBookmarkedPosts=cardList
        this.bookmarkedPostsList = allBookmarkedPosts.take(10).toMutableList()
        notifyDataSetChanged()
    }

    fun addMoreCards(newPostList:List<BookmarkedPostsEntity>) {
        val currentSize = bookmarkedPostsList.size
        val newItemSize=newPostList.size
        bookmarkedPostsList.addAll(newPostList)
        notifyItemRangeInserted(currentSize,newItemSize)
    }
    inner class SavedBossTalkCardViewHolder(private val binding: RvItemSavedBossBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(post: BookmarkedPostsEntity) {
            val postText = "Q. ${post.title}"

            // Q. 부분 색상 설정
            val spannable = SpannableString(postText)
            val purpleColor = ContextCompat.getColor(binding.root.context, R.color.Purple600)
            val grayColor = ContextCompat.getColor(binding.root.context, R.color.Gray700)

            val colorSpanQ = ForegroundColorSpan(purpleColor)
            val colorSpanRest = ForegroundColorSpan(grayColor)

            spannable.setSpan(colorSpanQ, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(colorSpanRest, 2, postText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            binding.tvBossTalkTitle.text = spannable
            binding.tvBossTalkContent.text = post.content
            binding.tvBossTalkDate.text = LocalDateFormatter.extractDate(post.createdAt)

            binding.tvBossTalkBookmarkCount.text = post.bookmarkCount.toString()
            binding.tvBossTalkCommentCount.text = post.commentCount.toString()
            binding.tvBossTalkLikeCount.text = post.likeCount.toString()

            binding.icBossTalkBookmark.isSelected = post.bookmarked
            binding.tvBossTalkBookmarkCount.isSelected = post.bookmarked

            binding.icBossTalkLike.isSelected = post.liked
            binding.tvBossTalkLikeCount.isSelected = post.liked

            // 상세 글 이동
            binding.root.setOnClickListener {
                val intent = Intent(context, BossTalkBodyActivity::class.java).apply {
                    putExtra(BOSS_POSTID, post.postId)
                }
                context.startActivity(intent)
            }
        }
    }
}