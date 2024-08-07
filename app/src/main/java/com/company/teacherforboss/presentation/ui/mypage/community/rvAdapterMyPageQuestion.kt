package com.company.teacherforboss.presentation.ui.mypage.community

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.company.teacherforboss.databinding.RvItemExchangeHistoryBinding
import com.company.teacherforboss.databinding.RvItemMyPageQuestionCardBinding
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity
import com.company.teacherforboss.domain.model.mypage.MyPageQuestionEntity
import com.company.teacherforboss.presentation.ui.mypage.MyPageViewModel

class rvAdapterMyPageQuestion(
    private val context: Context,
    private val questionList: List<MyPageQuestionEntity>
) : RecyclerView.Adapter<rvAdapterMyPageQuestion.ViewHolder>() {
    inner class ViewHolder(private val binding: RvItemMyPageQuestionCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (question: MyPageQuestionEntity){
            binding.apply {
                tvMyPageQuestionTitle.text = question.title
                tvMyPageQuestionBody.text = question.content
                tvMyPageQuestionDate.text = question.createdAt
                tvQuestionCategory.text = question.category
                if(question.solved) {
                    widgetCardViewStatementSolved.visibility = View.VISIBLE
                    Glide.with(root.context)
                        .load(question.selectedTeacher)
                        .into(ivSelectedTeacher)
                }
                else widgetCardViewStatementNotSolved.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemMyPageQuestionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questionList.get(position))
    }


}