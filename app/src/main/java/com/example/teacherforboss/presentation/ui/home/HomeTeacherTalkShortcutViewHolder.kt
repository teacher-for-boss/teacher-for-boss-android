package com.example.teacherforboss.presentation.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemHomeTeacherTalkShortcutBinding
import com.example.teacherforboss.presentation.model.TeacherTalkShortCutModel

class HomeTeacherTalkShortcutViewHolder(
    private val binding: ItemHomeTeacherTalkShortcutBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: TeacherTalkShortCutModel) {
        binding.teacherTalkShortcutItem = item
        binding.ivCategoryImage.setImageResource(item.shortCutImage)
    }
}
