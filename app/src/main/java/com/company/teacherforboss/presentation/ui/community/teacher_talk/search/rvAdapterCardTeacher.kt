package com.company.teacherforboss.presentation.ui.community.teacher_talk.search

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemTeacherSearchCardBinding
import com.company.teacherforboss.domain.model.community.teacher.QuestionEntity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyActivity
import com.company.teacherforboss.util.base.LocalDateFormatter

class rvAdapterCardTeacher(private val context: Context, private val questionList: List<QuestionEntity>
): RecyclerView.Adapter<rvAdapterCardTeacher.ViewHolder>() {
    class ViewHolder(private val binding: RvItemTeacherSearchCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, question: QuestionEntity) {

            // init View
            binding.title.text = setTextColor(context.getString(R.string.teacher_talk_card_view_question, question.title))
            binding.content.text = question.content
            binding.date.text = LocalDateFormatter.extractDate2(question.createdAt)
            binding.categoryName.text = question.category

            binding.answerCount.text = question.answerCount.toString()
            binding.likeCount.text = question.likeCount.toString()
            binding.bookmarkCount.text = question.bookmarkCount.toString()

            if(question.liked) binding.likeIv.isSelected = true
            if(question.bookmarked) binding.bookmarkIv.isSelected = true

            // 질문 상세보기
            binding.root.setOnClickListener {
                val intent = Intent(context, TeacherTalkBodyActivity::class.java).apply {
                    putExtra("PREVIOUS_ACTIVITY", "TeacherTalkSearchActivity")
                    putExtra("questionId", question.questionId.toString())
                }
                context.startActivity(intent)
            }
        }

        @SuppressLint("ResourceAsColor")
        fun setTextColor(text: String): SpannableString {
            // 텍스트에 색상입히기
            val spannableString = SpannableString(text)

            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#8D37EF")),
                0,
                2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
            return spannableString
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemTeacherSearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context = context, question = questionList[position])
    }
}