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

class TeacherTalkCategoryAdapter(
    private val context: Context,
    private val categoryList: ArrayList<String>,
    private val viewModel: TeacherTalkMainViewModel
) : RecyclerView.Adapter<TeacherTalkCategoryAdapter.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    var selectedItemPosition = DEFAULT_TAG_POSITION
    var previousItemPosition = RecyclerView.NO_POSITION

    init {
        selectedItemPosition = viewModel.categoryId.value!!.toInt()
    }

    inner class ViewHolder(private val binding: ItemTeacherTalkCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.teacherTalkCategory.setOnClickListener {
                previousItemPosition = selectedItemPosition
                selectedItemPosition = adapterPosition

                val selectedCategory = categoryList[selectedItemPosition]
                viewModel.setCategory(selectedCategory,0L)

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
                binding.teacherTalkCategory.setTextColor(Color.parseColor("#7B79E8"))  // purple500
                binding.teacherTalkCategory.setBackgroundResource(R.drawable.background_radius8_purple200)
            } else {
                binding.teacherTalkCategory.setTextColor(Color.parseColor("#7B79E8"))  // purple500
                binding.teacherTalkCategory.setBackgroundResource(R.drawable.background_radius8_purple200)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeacherTalkCategoryAdapter.ViewHolder {
        val binding = ItemTeacherTalkCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherTalkCategoryAdapter.ViewHolder, position: Int) {
        holder.bind(categoryList[position], position)
    }

    override fun getItemCount() = categoryList.size

    companion object {
        const val DEFAULT_TAG_POSITION = 0
    }
}