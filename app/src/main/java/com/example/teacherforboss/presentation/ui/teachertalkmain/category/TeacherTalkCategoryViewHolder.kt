package com.example.teacherforboss.presentation.ui.teachertalkmain.category

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemTeacherTalkCategoryBinding

class TeacherTalkCategoryViewHolder(private val binding: ItemTeacherTalkCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun onBind(teacherTalkChipData: TeacherTalkCategory) {
                binding.chipTeacherTalkCategory.text= teacherTalkChipData.content
            }
        }



