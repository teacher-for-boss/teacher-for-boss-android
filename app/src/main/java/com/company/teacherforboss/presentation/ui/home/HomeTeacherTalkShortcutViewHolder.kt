package com.company.teacherforboss.presentation.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.ItemHomeTeacherTalkShortcutBinding
import com.company.teacherforboss.presentation.model.TeacherTalkShortCutModel

class HomeTeacherTalkShortcutViewHolder(
    private val binding: ItemHomeTeacherTalkShortcutBinding,
    private val itemClickListener: ItemClickListener

) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: TeacherTalkShortCutModel) {
        binding.apply{
            teacherTalkShortcutItem = item
            ivCategoryImage.setImageResource(item.shortCutImage)
            binding.viewCategory.setOnClickListener {
                if (tvCategoryName.text in listOf("전체", "세무", "노무", "노하우")) {
                    itemClickListener?.onItemClicked(binding.tvCategoryName.text.toString())
                }
            }
        }


    }
}
