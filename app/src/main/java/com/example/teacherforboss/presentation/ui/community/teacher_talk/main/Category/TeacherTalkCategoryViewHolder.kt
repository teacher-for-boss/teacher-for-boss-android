package com.example.teacherforboss.presentation.ui.community.teacher_talk.main.Category

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemTeacherTalkCategoryBinding

class TeacherTalkCategoryViewHolder(private val binding: ItemTeacherTalkCategoryBinding) :
RecyclerView.ViewHolder(binding.root) {

//    init {
//
//        binding.teacherTalkCategory.setOnClickListener {
//            previousItemPosition = selectedItemPosition
//            selectedItemPosition = adapterPosition
//
//            //bind에 보내는 함수
//            notifyItemChanged(previousItemPosition)
//            notifyItemChanged(selectedItemPosition)
//        }
//    }
    fun onBind(categoryData: TeacherTalkCategory) {
            binding.teacherTalkCategory.text= categoryData.category_name
    }
}
