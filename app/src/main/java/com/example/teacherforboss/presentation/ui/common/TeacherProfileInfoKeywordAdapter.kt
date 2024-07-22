package com.example.teacherforboss.presentation.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.teacherforboss.databinding.ItemTeacherProfileKeywordBinding
import com.example.teacherforboss.util.view.ItemDiffCallback

class TeacherProfileInfoKeywordAdapter :
    ListAdapter<String, TeacherProfileInfoKeywordViewHolder>(
        ItemDiffCallback<String>(
            onItemsTheSame = { old, new -> old == new },
            onContentsTheSame = { old, new -> old == new },
        ),
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TeacherProfileInfoKeywordViewHolder = TeacherProfileInfoKeywordViewHolder(
        ItemTeacherProfileKeywordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    )

    override fun onBindViewHolder(holder: TeacherProfileInfoKeywordViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
