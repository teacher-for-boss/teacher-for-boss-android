package com.example.teacherforboss.presentation.ui.community.teachertalk_main.card

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemTeacherTalkCardBinding

class TeacherTalkMainCardViewHolder(private val binding: ItemTeacherTalkCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(cardData: TeacherTalkCard) {
        with(binding) {
            tvTeacherTalkQuestion.text = "Q. " + cardData.question
            tvTeacherTalkText.text = cardData.answer
            tvCardViewStatement.text = cardData.statement_answer
            tvBookmarkCount.text = cardData.count_bookmark
            tvLikeCount.text = cardData.count_like
            tvCommentCount.text = cardData.count_comment
        }
    }
}
