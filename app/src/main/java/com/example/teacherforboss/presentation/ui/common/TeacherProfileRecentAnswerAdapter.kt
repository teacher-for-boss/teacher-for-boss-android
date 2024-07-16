package com.example.teacherforboss.presentation.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.teacherforboss.databinding.ItemTeacherProfileRecentAnswerBinding
import com.example.teacherforboss.domain.model.common.TeacherRecentAnswerListEntity.TeacherRecentAnswer
import com.example.teacherforboss.util.view.ItemDiffCallback

class TeacherProfileRecentAnswerAdapter :
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
    )

    override fun onBindViewHolder(
        holder: TeacherProfileRecentAnswerViewHolder,
        position: Int,
    ) {
        holder.onBind(getItem(position))
    }
}
