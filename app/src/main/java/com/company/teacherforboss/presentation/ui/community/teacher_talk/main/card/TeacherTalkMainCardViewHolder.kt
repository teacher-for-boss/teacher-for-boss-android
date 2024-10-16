package com.company.teacherforboss.presentation.ui.community.teacher_talk.main.card

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ItemTeacherTalkCardBinding
import com.company.teacherforboss.domain.model.community.teacher.QuestionEntity
import java.time.format.DateTimeFormatter

class TeacherTalkMainCardViewHolder(private val binding: ItemTeacherTalkCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(cardData: QuestionEntity) {
        with(binding) {
            tvAnsweredQuestionTitle.text = "Q. " + cardData.title
            tvAnsweredQuestionContent.text = cardData.content
            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
            val formattedDate = cardData.createdAt.format(formatter)
            tvAnsweredQuestionDate.text = formattedDate
            tvTeacherTalkBookmarkCount.text = cardData.bookmarkCount.toString()
            tvTeacherTalkLikeCount.text = cardData.likeCount.toString()
            tvTeacherTalkAnswerCount.text = cardData.answerCount.toString()
        }
        if(cardData.liked) binding.icTeacherTalkLike.setImageResource(R.drawable.ic_like_on)
        if(cardData.bookmarked) binding.icTeacherTalkBookmark.setImageResource(R.drawable.ic_bookmark_on)

        if(cardData.solved)
            binding.widgetCardViewStatementSolved.visibility = View.VISIBLE
        else binding.widgetCardViewStatementNotSolved.visibility = View.VISIBLE

    }

    fun extractDate(text:String):String{
        val pattern="\\d{4}-\\d{2}-\\d{2}".toRegex()
        val match=pattern.find(text)
        return match?.value?:""
    }
}
