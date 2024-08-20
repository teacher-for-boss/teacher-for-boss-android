package com.company.teacherforboss.presentation.ui.mypage.boss_talk

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.RvItemBossSearchCardBinding
import com.company.teacherforboss.domain.model.community.teacher.QuestionEntity
import com.company.teacherforboss.domain.model.mypage.MyPagePostEntity
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyActivity
import com.company.teacherforboss.util.base.LocalDateFormatter

class rvAdapterBoss(
    private val context: Context,
    private val postList: MutableList<MyPagePostEntity>
): RecyclerView.Adapter<rvAdapterBoss.ViewHolder>() {
    class ViewHolder(private val binding: RvItemBossSearchCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, post: MyPagePostEntity) {

            // init View
            binding.title.text = post.title
            binding.content.text = post.content
            binding.date.text = LocalDateFormatter.extractDate2(post.createdAt)

            binding.commentCount.text = post.commentCount.toString()
            binding.likeCount.text = post.likeCount.toString()
            binding.bookmarkCount.text = post.bookmarkCount.toString()

            if (post.liked) binding.likeIv.isSelected = true
            if (post.bookmarked) binding.bookmarkIv.isSelected = true

            // 본문 상세보기
            binding.root.setOnClickListener {
                val intent = Intent(context, BossTalkBodyActivity::class.java).apply {
                    putExtra("PREVIOUS_ACTIVITY", "BossTalkSearchActivity")
                    putExtra("postId", post.postId.toString())
                }
                context.startActivity(intent)
            }
        }
    }
    fun addMoreCards(newPostList:List<MyPagePostEntity>) {
        val currentSize = postList.size
        val newItemSize= newPostList.size
        postList.addAll(newPostList)
        notifyItemRangeInserted(currentSize,newItemSize)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            RvItemBossSearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context = context, post = postList[position])
    }
}