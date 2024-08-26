package com.company.teacherforboss.presentation.ui.community.teacher_talk.main.card

import android.content.Context
import android.content.Intent
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ItemTeacherTalkCardBinding
import com.company.teacherforboss.domain.model.community.teacher.QuestionEntity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyActivity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_QUESTIONID
import com.company.teacherforboss.util.base.LocalDateFormatter

class TeacherTalkCardAdapter(
    private val clickItem: (Long) -> Unit) :
    RecyclerView.Adapter<TeacherTalkCardAdapter.TeacherTalkMainCardViewHolder>() {

    private var teacherTalkCardList: MutableList<QuestionEntity> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeacherTalkMainCardViewHolder=TeacherTalkMainCardViewHolder(
        binding = ItemTeacherTalkCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        clickItem = clickItem
    )

    override fun onBindViewHolder(holder: TeacherTalkMainCardViewHolder, position: Int) {
        holder.onBind(teacherTalkCardList[position])
    }

    override fun getItemCount(): Int {
        return teacherTalkCardList.size
    }

    fun setCardList(cardList: List<QuestionEntity>) {
        this.teacherTalkCardList = cardList.take(10).toMutableList()
        notifyDataSetChanged()
    }

    fun addMoreCards(newQuestionList:List<QuestionEntity>) {
        val currentSize = teacherTalkCardList.size
        val newItemSize=newQuestionList.size
        if(newItemSize>0){
            teacherTalkCardList.addAll(newQuestionList)
            notifyItemRangeInserted(currentSize,newItemSize)
        }
    }

    inner class TeacherTalkMainCardViewHolder(private val binding: ItemTeacherTalkCardBinding,clickItem: (Long) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(teacherTalkCard: QuestionEntity) {
            val questionText = "Q. ${teacherTalkCard.title}"

            // Q. 부분 색상 설정
            val spannable = SpannableString(questionText)
            val purpleColor = ContextCompat.getColor(binding.root.context, R.color.Purple600)
            val grayColor = ContextCompat.getColor(binding.root.context, R.color.Gray700)

            val colorSpanQ = ForegroundColorSpan(purpleColor)
            val colorSpanRest = ForegroundColorSpan(grayColor)

            spannable.setSpan(colorSpanQ, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(colorSpanRest, 2, questionText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            with(binding){
                tvAnsweredQuestionTitle.text = spannable
                tvAnsweredQuestionContent.text = teacherTalkCard.content
                tvTeacherTalkBookmarkCount.text = teacherTalkCard.bookmarkCount.toString()
                tvTeacherTalkLikeCount.text = teacherTalkCard.likeCount.toString()
                tvTeacherTalkAnswerCount.text = teacherTalkCard.answerCount.toString()
                tvAnsweredQuestionDate.text = LocalDateFormatter.extractDate(teacherTalkCard.createdAt)

                icTeacherTalkBookmark.isSelected = teacherTalkCard.bookmarked
                tvTeacherTalkBookmarkCount.isSelected = teacherTalkCard.bookmarked

                icTeacherTalkLike.isSelected = teacherTalkCard.liked
                tvTeacherTalkLikeCount.isSelected = teacherTalkCard.liked
            }

            if(teacherTalkCard.solved) {
                binding.widgetCardViewStatementSolved.visibility = View.VISIBLE
                Glide.with(binding.root.context)
                    .load(teacherTalkCard.selectedTeacher)
                    .into(binding.ivSelectedTeacher)
            }
            else binding.widgetCardViewStatementNotSolved.visibility = View.VISIBLE

            // 상세 글 이동
            binding.root.setOnClickListener {
                clickItem(teacherTalkCard.questionId)
            }

        }
    }
}
