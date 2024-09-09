package com.company.teacherforboss.presentation.ui.community.teacher_talk.main.Category

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ItemTeacherTalkCategoryBinding
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.TeacherTalkMainViewModel
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_LASTID

class TeacherTalkCategoryAdapter(
    private val categoryList: ArrayList<String>,
    private val initCategoryPosition:Int,
    private val clickCategory:(String)->Unit
) : RecyclerView.Adapter<TeacherTalkCategoryAdapter.ViewHolder>() {
    var selectedItemPosition = DEFAULT_TAG_POSITION
    var previousItemPosition = RecyclerView.NO_POSITION

    init {
        selectedItemPosition=initCategoryPosition
    }

    inner class ViewHolder(private val binding: ItemTeacherTalkCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.teacherTalkCategory.setOnClickListener {
                previousItemPosition = selectedItemPosition
                selectedItemPosition = adapterPosition

                val selectedCategory = categoryList[selectedItemPosition]
                clickCategory(selectedCategory)
                notifyItemChanged(previousItemPosition)
                notifyItemChanged(selectedItemPosition)
            }
        }

        @SuppressLint("ResourceAsColor")
        fun bind(category: String, position: Int) {
            binding.teacherTalkCategory.text = category

            if (position == selectedItemPosition) {
                binding.teacherTalkCategory.setTextColor(Color.WHITE)
                binding.teacherTalkCategory.setBackgroundResource(R.drawable.background_radius8_purple600)
            } else if (position == previousItemPosition) {
                binding.teacherTalkCategory.setTextColor(Color.parseColor("#A15AF2"))  // purple500
                binding.teacherTalkCategory.setBackgroundResource(R.drawable.background_radius8_purple200)
            } else {
                binding.teacherTalkCategory.setTextColor(Color.parseColor("#A15AF2"))  // purple500
                binding.teacherTalkCategory.setBackgroundResource(R.drawable.background_radius8_purple200)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeacherTalkCategoryAdapter.ViewHolder= ViewHolder(
        ItemTeacherTalkCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: TeacherTalkCategoryAdapter.ViewHolder, position: Int) {
        holder.bind(categoryList[position], position)
    }

    override fun getItemCount() = categoryList.size

    companion object {
        const val DEFAULT_TAG_POSITION = 0
    }
}