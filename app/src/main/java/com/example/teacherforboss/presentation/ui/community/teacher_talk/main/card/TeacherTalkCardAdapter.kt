package com.example.teacherforboss.presentation.ui.community.teacher_talk.main.card

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
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ItemTeacherTalkCardBinding
import com.example.teacherforboss.domain.model.community.teacher.QuestionEntity
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.TeachertalkBodyActivity
import com.example.teacherforboss.util.base.LocalDateFormatter

class TeacherTalkCardAdapter(context: Context) :
    RecyclerView.Adapter<TeacherTalkCardAdapter.TeacherTalkMainCardViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var teacherTalkCardList: MutableList<QuestionEntity> = mutableListOf()
    private var allTeacherTalkCard: List<QuestionEntity> = emptyList()
    private val context=context

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

    override fun getItemCount(): Int {
        return teacherTalkCardList.size
    }

    fun setCardList(cardList: List<QuestionEntity>) {
        this.allTeacherTalkCard = cardList
        this.teacherTalkCardList = allTeacherTalkCard.take(10).toMutableList()
        notifyDataSetChanged()
    }

    fun addMoreCards() {
        val currentSize = teacherTalkCardList.size
        val nextSize = minOf(currentSize + 10, allTeacherTalkCard.size)
        if (currentSize < nextSize) {
            teacherTalkCardList.addAll(allTeacherTalkCard.subList(currentSize, nextSize))
            notifyItemRangeInserted(currentSize, nextSize - currentSize)
        }
    }

    inner class TeacherTalkMainCardViewHolder(private val binding: ItemTeacherTalkCardBinding) :
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

            binding.tvTeacherTalkQuestion.text = spannable
            binding.tvTeacherTalkContent.text = teacherTalkCard.content
            binding.tvTeacherTalkBookmarkCount.text = teacherTalkCard.bookmarkCount.toString()
            binding.tvTeacherTalkLikeCount.text = teacherTalkCard.likeCount.toString()
            binding.tvTeacherTalkAnswerCount.text = teacherTalkCard.answerCount.toString()
            binding.tvTeacherTalkDate.text = LocalDateFormatter.extractDate(teacherTalkCard.createdAt)

            binding.icTeacherTalkBookmark.isSelected = teacherTalkCard.bookmarked
            binding.tvTeacherTalkBookmarkCount.isSelected = teacherTalkCard.bookmarked

            binding.icTeacherTalkLike.isSelected = teacherTalkCard.liked
            binding.tvTeacherTalkLikeCount.isSelected = teacherTalkCard.liked

            if(teacherTalkCard.solved) {
                binding.widgetCardViewStatementSolved.visibility = View.VISIBLE
                Glide.with(binding.root.context)
                    .load(teacherTalkCard.selectedTeacher)
                    .into(binding.ivSelectedTeacher)
            }
            else binding.widgetCardViewStatementNotSolved.visibility = View.VISIBLE

            // 상세 글 이동
            binding.root.setOnClickListener {
                val intent= Intent(context, TeachertalkBodyActivity::class.java).apply{
                    putExtra("questionId",teacherTalkCard.questionId.toString())
                }
                context.startActivity(intent)
            }

        }
    }
}
