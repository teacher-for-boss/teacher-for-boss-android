package com.example.teacherforboss.presentation.ui.teachertalkmain.card

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ItemTeacherTalkCardBinding

class TeacherTalkMainCardViewHolder(private val binding: ItemTeacherTalkCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(cardData: TeacherTalkCard) {
        with(binding) {
            // Q. 부분 색상 설정
            val questionText = "Q. ${cardData.question}"
            val spannable = SpannableString(questionText)
            val purpleColor = ContextCompat.getColor(root.context, R.color.Purple600)
            val grayColor = ContextCompat.getColor(root.context, R.color.Gray700)

            spannable.setSpan(ForegroundColorSpan(purpleColor), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(ForegroundColorSpan(grayColor), 2, questionText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            tvTeacherTalkQuestion.text = spannable

            // 다른 텍스트 설정
            tvTeacherTalkText.text = cardData.answer
            tvTeacherTalkDate.text = cardData.date
            tvTeacherTalkCardViewStatement.text = cardData.statement_answer
            tvTeacherTalkBookmarkCount.text = cardData.count_bookmark
            tvTeacherTalkLikeCount.text = cardData.count_like
            tvTeacherTalkCommentCount.text = cardData.count_comment
        }
    }
}
