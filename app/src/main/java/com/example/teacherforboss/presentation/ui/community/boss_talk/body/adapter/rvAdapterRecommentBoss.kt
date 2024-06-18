package com.example.teacherforboss.presentation.ui.community.boss_talk.body.adapter

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.RvItemCommentBossBinding
import com.example.teacherforboss.databinding.RvItemRecommentBossBinding

class rvAdapterRecommentBoss(private val commentList: List<String>): RecyclerView.Adapter<rvAdapterRecommentBoss.ViewHolder>() {

    inner class ViewHolder(private val binding: RvItemRecommentBossBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: String) {
            binding.userName.text = comment

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

            //추천비추천
            var isCommentGood = false
            var isCommentBad = false
            fun updateComment() {
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
            binding.commentGood.setOnClickListener {
                isCommentGood = !isCommentGood
                if(isCommentGood && isCommentBad) {
                    isCommentBad = !isCommentBad
                }
                updateComment()
            }
            binding.commentBad.setOnClickListener {
                isCommentBad = !isCommentBad
                if(isCommentGood && isCommentBad) {
                    isCommentGood = !isCommentGood
                }
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