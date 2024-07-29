package com.company.teacherforboss.presentation.ui.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.company.teacherforboss.databinding.ItemTeacherProfileRecentAnswerBinding
import com.company.teacherforboss.domain.model.common.TeacherRecentAnswerListEntity.TeacherRecentAnswer
import com.company.teacherforboss.util.view.ItemDiffCallback

class TeacherProfileRecentAnswerAdapter(
    private val context: Context,
) :
    ListAdapter<TeacherRecentAnswer, TeacherProfileRecentAnswerViewHolder>(
        ItemDiffCallback<TeacherRecentAnswer>(
            onItemsTheSame = { old, new -> old.questionId == new.questionId },
            onContentsTheSame = { old, new -> old == new },
        ),
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TeacherProfileRecentAnswerViewHolder = TeacherProfileRecentAnswerViewHolder(
        ItemTeacherProfileRecentAnswerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
        context,
    )

    override fun onBindViewHolder(
        holder: TeacherProfileRecentAnswerViewHolder,
        position: Int,
    ) {
        holder.onBind(getItem(position))
    }
}
