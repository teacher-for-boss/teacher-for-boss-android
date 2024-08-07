package com.company.teacherforboss.presentation.ui.mypage.saved

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
import com.company.teacherforboss.util.base.LocalDateFormatter

class SavedTeacherTalkCardAdapter(context: Context) :
    RecyclerView.Adapter<SavedTeacherTalkCardAdapter.SavedTeacherTalkCardViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var teacherTalkCardList: MutableList<QuestionEntity> = mutableListOf()
    private var allTeacherTalkCard: List<QuestionEntity> = emptyList()
    private val context=context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedTeacherTalkCardViewHolder {
        val binding = ItemTeacherTalkCardBinding.inflate(inflater, parent, false)
        return SavedTeacherTalkCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedTeacherTalkCardViewHolder, position: Int) {
        holder.onBind(teacherTalkCardList[position])
    }

    override fun getItemCount(): Int {
        return teacherTalkCardList.size
    }

    inner class SavedTeacherTalkCardViewHolder(private val binding: ItemTeacherTalkCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(teacherTalkCard: QuestionEntity) { // 나중에 수정
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
                val intent= Intent(context, TeacherTalkBodyActivity::class.java).apply{
                    putExtra("questionId",teacherTalkCard.questionId.toString())
                }
                context.startActivity(intent)
            }

        }
    }
}
