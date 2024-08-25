package com.company.teacherforboss.presentation.ui.mypage.community

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.company.teacherforboss.databinding.RvItemMyPageQuestionCardBinding
import com.company.teacherforboss.domain.model.mypage.MyPageQuestionEntity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyActivity
import com.company.teacherforboss.util.base.LocalDateFormatter

class rvAdapterMyPageQuestion(
    private val context: Context,
    private val questionList: MutableList<MyPageQuestionEntity>
) : RecyclerView.Adapter<rvAdapterMyPageQuestion.ViewHolder>() {
    inner class ViewHolder(private val binding: RvItemMyPageQuestionCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (question: MyPageQuestionEntity){
            with(binding) {
                tvMyPageQuestionTitle.text = question.title
                tvMyPageQuestionBody.text = question.content
                tvMyPageQuestionDate.text = question.createdAt
                tvQuestionCategory.text = question.category
                tvMyPageQuestionDate.text = LocalDateFormatter.extractDate2(question.createdAt)

                if(question.solved) {
                    widgetCardViewStatementSolved.visibility = View.VISIBLE
                    Glide.with(root.context)
                        .load(question.selectedTeacher)
                        .into(ivSelectedTeacher)
                }
                else widgetCardViewStatementNotSolved.visibility = View.VISIBLE

                root.setOnClickListener {
                    val intent = Intent(context, TeacherTalkBodyActivity::class.java).apply {
                        putExtra("questionId", question.questionId)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
    fun addMoreCards(newQuestionList:List<MyPageQuestionEntity>) {
        val currentSize = questionList.size
        val newItemSize= newQuestionList.size
        questionList.addAll(newQuestionList)
        notifyItemRangeInserted(currentSize,newItemSize)

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