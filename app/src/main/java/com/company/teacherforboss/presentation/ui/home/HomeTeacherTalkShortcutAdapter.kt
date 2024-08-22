package com.company.teacherforboss.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.company.teacherforboss.databinding.ItemHomeTeacherTalkShortcutBinding
import com.company.teacherforboss.presentation.model.TeacherTalkShortCutModel
import com.company.teacherforboss.util.view.ItemDiffCallback

class HomeTeacherTalkShortcutAdapter(private val itemClickListener: ItemClickListener) :
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
        itemClickListener
    )

    override fun onBindViewHolder(holder: HomeTeacherTalkShortcutViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
