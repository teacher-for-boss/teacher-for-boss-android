package com.example.teacherforboss.presentation.ui.community.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.RvItemCommentBinding
import com.example.teacherforboss.presentation.ui.community.TeacherTalkBodyViewModel

class rvAdapterComment(private val AnswerList: List<TeacherTalkBodyViewModel.Answer>,
                       private val viewModel:TeacherTalkBodyViewModel,
                       private val lifecycleOwner: LifecycleOwner
    ): RecyclerView.Adapter<rvAdapterComment.ViewHolder>() {
    class ViewHolder(private val binding: RvItemCommentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(teacher: TeacherTalkBodyViewModel.Answer, viewModel: TeacherTalkBodyViewModel, lifecycleOwner: LifecycleOwner) {
            binding.userName.text = teacher.name

            binding.commentGood.setOnClickListener { viewModel.clickAnswerGood() }
            binding.commentBad.setOnClickListener { viewModel.clickAnswerBad() }

            viewModel.isAnswerGood.observe(lifecycleOwner, Observer{ isGood ->
                if(isGood) {
                    binding.commentGoodTv.setTextColor(Color.parseColor("#5F5CE8"))
                    binding.commentGoodIv.setImageResource(R.drawable.comment_good_on)
                }
                else {
                    binding.commentGoodTv.setTextColor(Color.parseColor("#8490A0"))
                    binding.commentGoodIv.setImageResource(R.drawable.comment_good)
                }
            })

            viewModel.isAnswerBad.observe(lifecycleOwner, Observer{ isBad ->
                if(isBad) {
                    binding.commentBadTv.setTextColor(Color.parseColor("#5F5CE8"))
                    binding.commentBadIv.setImageResource(R.drawable.comment_bad_on)
                }
                else {
                    binding.commentBadTv.setTextColor(Color.parseColor("#8490A0"))
                    binding.commentBadIv.setImageResource(R.drawable.comment_bad)
                }
            })

            //더보기 버튼
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
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return AnswerList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(AnswerList[position], viewModel, lifecycleOwner)

    }
}