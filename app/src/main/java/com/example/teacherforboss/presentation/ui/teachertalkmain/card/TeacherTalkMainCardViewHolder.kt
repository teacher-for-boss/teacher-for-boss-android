package com.example.teacherforboss.presentation.ui.teachertalkmain.card

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemTeacherTalkCardBinding

class TeacherTalkMainCardViewHolder(private val binding: ItemTeacherTalkCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(cardData: TeacherTalkCard) {
        with(binding) {
            tvTeacherTalkQuestion.text = "Q. " + cardData.question
            tvTeacherTalkText.text = cardData.answer
            tvTeacherTalkDate.text = cardData.date
            tvTeacherTalkCardViewStatement.text = cardData.statement_answer
            tvTeacherTalkBookmarkCount.text = cardData.count_bookmark
            tvTeacherTalkLikeCount.text = cardData.count_like
            tvTeacherTalkCommentCount.text = cardData.count_comment
        }
    }
}
