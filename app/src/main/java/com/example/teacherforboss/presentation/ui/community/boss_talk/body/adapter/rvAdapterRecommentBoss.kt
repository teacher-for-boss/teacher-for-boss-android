package com.example.teacherforboss.presentation.ui.community.boss_talk.body.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.RvItemRecommentBossBinding
import com.example.teacherforboss.domain.model.community.CommentEntity
import com.example.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyViewModel
import com.example.teacherforboss.util.base.BindingImgAdapter
import com.example.teacherforboss.util.base.LocalDateFormatter

class rvAdapterRecommentBoss(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context,
    private val commentList: List<CommentEntity>,
    private val viewModel: BossTalkBodyViewModel
): RecyclerView.Adapter<rvAdapterRecommentBoss.ViewHolder>() {

    inner class ViewHolder(private val binding: RvItemRecommentBossBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentEntity) {

            // 유저 정보
            val member=comment.memberInfo
            binding.userName.text = member.name
            member.profileImg?.let {
                if(it!="") BindingImgAdapter.bindImage(binding.userImage,it)
            }

            // 날짜
            binding.createdAt.text=LocalDateFormatter.extractDate(comment.createdAt)

            // 댓글 본문
            binding.commentBody.text=comment.content

            // 추천, 비추천 갯수
            binding.commentGoodTv.text=context.getString(R.string.recommed_option,comment.likeCount)
            binding.commentBadTv.text=context.getString(R.string.not_recommed_option,comment.dislikeCount)

            //신고하기
            binding.btnOption.setOnClickListener {
                if (binding.reportBtn.visibility == View.GONE) {
                    binding.reportBtn.visibility = View.VISIBLE
                } else {
                    binding.reportBtn.visibility = View.GONE
                }
            }
            binding.reportBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/3Tr8cfAoWC2949aMA"))
                binding.root.context.startActivity(intent)
            }

            //사용자의 추천 비추천 여부
            var isCommentGood = comment.liked
            var isCommentBad = comment.disliked

            fun handleCommentBtnColor(){
                if(isCommentGood) {
                    binding.commentGoodTv.setTextColor(Color.parseColor("#5F5CE8"))
                    binding.commentGoodIv.setImageResource(R.drawable.comment_good_on)
                } else {
                    binding.commentGoodTv.setTextColor(Color.parseColor("#8490A0"))
                    binding.commentGoodIv.setImageResource(R.drawable.comment_good)
                }

                if(isCommentBad) {
                    binding.commentBadTv.setTextColor(Color.parseColor("#5F5CE8"))
                    binding.commentBadIv.setImageResource(R.drawable.comment_bad_on)
                } else {
                    binding.commentBadTv.setTextColor(Color.parseColor("#8490A0"))
                    binding.commentBadIv.setImageResource(R.drawable.comment_bad)
                }
            }

            handleCommentBtnColor()

            // 추천 비추천 onclick
            fun updateComment() {
                viewModel.getReCommentLikeLiveData(comment.commentId).observe(lifecycleOwner, Observer {
                    // 추천,비추천 개수 업데이트
                    binding.commentGoodTv.text = context.getString(R.string.recommed_option, it.likedCount)
                    binding.commentBadTv.text = context.getString(R.string.not_recommed_option, it.dislikedCount)
                    handleCommentBtnColor()
                })
            }

            binding.commentGood.setOnClickListener {
                isCommentGood = !isCommentGood
                if(isCommentGood && isCommentBad) {
                    isCommentBad = !isCommentBad
                }
                viewModel.postReCommentLike(comment.commentId)
                updateComment()
            }
            binding.commentBad.setOnClickListener {
                isCommentBad = !isCommentBad
                if(isCommentGood && isCommentBad) {
                    isCommentGood = !isCommentGood
                }
                viewModel.postReCommentDisLike(comment.commentId)
                updateComment()
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = RvItemRecommentBossBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

}