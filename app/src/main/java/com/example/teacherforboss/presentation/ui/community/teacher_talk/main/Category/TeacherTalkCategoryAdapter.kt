package com.example.teacherforboss.presentation.ui.community.teacher_talk.main.Category

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ItemTeacherTalkCategoryBinding
import com.example.teacherforboss.databinding.RvItemCategoryBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.TeacherTalkMainViewModel

class TeacherTalkCategoryAdapter(context: Context, private val categoryList:ArrayList<String>,
        private val viewModel: TeacherTalkMainViewModel,
) :RecyclerView.Adapter<TeacherTalkCategoryViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    var selectedItemPosition= DEFAULT_TAG_POSITION
    var previousItemPosition= RecyclerView.NO_POSITION

    private var teacherTalkCategoryList: List<TeacherTalkCategory> = emptyList()
    inner class ViewHolder(private val binding: ItemTeacherTalkCategoryBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.teacherTalkCategory.setOnClickListener {
                previousItemPosition = selectedItemPosition
                selectedItemPosition = adapterPosition

                viewModel.selectCategoryId(selectedItemPosition.toLong())

                Log.d("category", selectedItemPosition.toString())

                //bind에 보내는 함수
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
    ): TeacherTalkCategoryViewHolder {
        val binding = ItemTeacherTalkCategoryBinding.inflate(inflater, parent, false)
        return TeacherTalkCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherTalkCategoryViewHolder, position: Int) {
        holder.onBind(teacherTalkCategoryList[position])
    }

    override fun getItemCount() = teacherTalkCategoryList.size

    fun setTeacherTalkCategoryList(teacherTalkCategoryList: List<TeacherTalkCategory>) {
        this.teacherTalkCategoryList = teacherTalkCategoryList.toList()
        notifyDataSetChanged()
    }

    companion object{
        const val DEFAULT_TAG_POSITION = 0
    }
}


