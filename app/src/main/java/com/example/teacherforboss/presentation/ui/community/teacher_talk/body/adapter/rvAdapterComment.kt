package com.example.teacherforboss.presentation.ui.community.teacher_talk.body.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.RvItemCommentTeacherBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.answer.TeacherTalkAnswerActivity
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyViewModel
import com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog.DeleteCommentDialog

class rvAdapterComment(private val AnswerList: List<TeacherTalkBodyViewModel.Answer>,
                       private val viewModel: TeacherTalkBodyViewModel,
                       private val lifecycleOwner: LifecycleOwner, private val context: Context
): RecyclerView.Adapter<rvAdapterComment.ViewHolder>() {
    class ViewHolder(private val binding: RvItemCommentTeacherBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(teacher: TeacherTalkBodyViewModel.Answer,
                 viewModel: TeacherTalkBodyViewModel,
                 lifecycleOwner: LifecycleOwner,
                 context: Context) {
            binding.userName.text = teacher.name

            //추천 비추천
            //서버에서 가져와서 연결해주면 됨 -> 일단은 둘다 false로 설정함
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

            //더보기 버튼 보여주기
            binding.btnOption.setOnClickListener {
                //작성자인 경우
                if(binding.writerOption.visibility == View.GONE) {
                    binding.writerOption.visibility = View.VISIBLE
                }
                else {
                    binding.writerOption.visibility = View.GONE
                }
                //작성자가 아닌 경우
                if(binding.nonWriterOption.visibility == View.GONE) {
                    binding.nonWriterOption.visibility = View.VISIBLE
                }
                else {
                    binding.nonWriterOption.visibility = View.GONE
                }
            }

            //삭제하기
            binding.deleteBtn.setOnClickListener {
                val dialog = DeleteCommentDialog(binding.root.context)
                dialog.show()
            }

            //수정하기
            binding.modifyBtn.setOnClickListener {
                val intent = Intent(context, TeacherTalkAnswerActivity::class.java)
                context.startActivity(intent)
            }

            //신고하기
            binding.reportBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/3Tr8cfAoWC2949aMA"))
                context.startActivity(intent)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemCommentTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return AnswerList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(AnswerList[position], viewModel, lifecycleOwner, context)

    }
}