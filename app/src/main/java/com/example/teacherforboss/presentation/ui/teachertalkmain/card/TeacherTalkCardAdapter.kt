package com.example.teacherforboss.presentation.ui.teachertalkmain.card

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ItemTeacherTalkCardBinding

class TeacherTalkCardAdapter(context: Context) :
    RecyclerView.Adapter<TeacherTalkCardAdapter.TeacherTalkMainCardViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    var selectPos = -1

    private var teacherTalkCardList: List<TeacherTalkCard> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeacherTalkMainCardViewHolder {
        val binding = ItemTeacherTalkCardBinding.inflate(inflater, parent, false)
        return TeacherTalkMainCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherTalkMainCardViewHolder, position: Int) {
        holder.onBind(teacherTalkCardList[position])
    }

    override fun getItemCount() = teacherTalkCardList.size

    fun setCardList(cardList: List<TeacherTalkCard>) {
        this.teacherTalkCardList = cardList.toList()
        notifyDataSetChanged()
    }

    inner class TeacherTalkMainCardViewHolder(private val binding: ItemTeacherTalkCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(teacherTalkCard: TeacherTalkCard) {
            val questionText = "Q. ${teacherTalkCard.question}"

            // Q. 부분 색상 설정
            val spannable = SpannableString(questionText)
            val purpleColor = ContextCompat.getColor(binding.root.context, R.color.Purple600)
            val grayColor = ContextCompat.getColor(binding.root.context, R.color.Gray700)

            val colorSpanQ = ForegroundColorSpan(purpleColor)
            val colorSpanRest = ForegroundColorSpan(grayColor)

            spannable.setSpan(colorSpanQ, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Q. 부분 색상 변경
            spannable.setSpan(colorSpanRest, 2, questionText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // 나머지 텍스트 색상 변경

            binding.tvTeacherTalkQuestion.text = spannable
            binding.tvTeacherTalkText.text = teacherTalkCard.answer
            binding.tvTeacherTalkDate.text = teacherTalkCard.date
            binding.tvTeacherTalkCardViewStatement.text = teacherTalkCard.statement_answer
            binding.tvTeacherTalkBookmarkCount.text = teacherTalkCard.count_bookmark
            binding.tvTeacherTalkLikeCount.text = teacherTalkCard.count_like
            binding.tvTeacherTalkCommentCount.text = teacherTalkCard.count_comment
        }
    }
}

