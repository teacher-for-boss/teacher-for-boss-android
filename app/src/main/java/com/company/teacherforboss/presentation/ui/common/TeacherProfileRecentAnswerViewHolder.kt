package com.company.teacherforboss.presentation.ui.common

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ItemTeacherProfileRecentAnswerBinding
import com.company.teacherforboss.domain.model.common.TeacherRecentAnswerListEntity.TeacherRecentAnswer

class TeacherProfileRecentAnswerViewHolder(
    private val binding: ItemTeacherProfileRecentAnswerBinding,
    private val context: Context,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: TeacherRecentAnswer) {
        with(binding) {
            teacherRecentAnswer = item

            tvTeacherProfileRecentAnswerQuestion.text = SpannableString(
                context.getString(
                    R.string.teacher_profile_tab_recent_answer_question,
                    item.questionTitle,
                ),
            ).apply {
                setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            context,
                            R.color.Purple600,
                        ),
                    ),
                    START_SPAN_POSITION,
                    END_SPAN_POSITION,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                )
            }

            tvTeacherProfileRecentAnswerAnswer.text = SpannableString(
                context.getString(
                    R.string.teacher_profile_tab_recent_answer_answer,
                    item.answer,
                ),
            ).apply {
                setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            context,
                            R.color.Purple600,
                        ),
                    ),
                    START_SPAN_POSITION,
                    END_SPAN_POSITION,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                )
            }
        }
    }

    companion object {
        private const val START_SPAN_POSITION = 0
        private const val END_SPAN_POSITION = 2
    }
}
