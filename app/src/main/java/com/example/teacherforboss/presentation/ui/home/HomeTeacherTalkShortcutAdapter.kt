package com.example.teacherforboss.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.teacherforboss.databinding.ItemHomeTeacherTalkShortcutBinding
import com.example.teacherforboss.presentation.model.TeacherTalkShortCutModel
import com.example.teacherforboss.util.view.ItemDiffCallback

class HomeTeacherTalkShortcutAdapter() :
    ListAdapter<TeacherTalkShortCutModel, HomeTeacherTalkShortcutViewHolder>(
        ItemDiffCallback<TeacherTalkShortCutModel>(
            onItemsTheSame = { old, new -> old.shortCutTitle == new.shortCutTitle },
            onContentsTheSame = { old, new -> old == new },
        ),
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeTeacherTalkShortcutViewHolder = HomeTeacherTalkShortcutViewHolder(
        ItemHomeTeacherTalkShortcutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    )

    override fun onBindViewHolder(holder: HomeTeacherTalkShortcutViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
