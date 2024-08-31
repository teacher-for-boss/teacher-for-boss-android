package com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemCategoryBinding
import com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.TeacherTalkAskViewModel

class rvAdapterCategory(
    private val categoryList: ArrayList<String>,
    private var selectedItemPosition: Int,
    private val selectCategory:(Long)->Unit,
): RecyclerView.Adapter<rvAdapterCategory.ViewHolder>(){

    var previousItemPosition = RecyclerView.NO_POSITION

    inner class ViewHolder(private val binding: RvItemCategoryBinding):RecyclerView.ViewHolder(binding.root) {

        init {
            binding.category.setOnClickListener {
                previousItemPosition = selectedItemPosition
                selectedItemPosition = adapterPosition

                selectCategory(selectedItemPosition.toLong())

                Log.d("cagtegory", selectedItemPosition.toString())

                //bind에 보내는 함수
                notifyItemChanged(previousItemPosition)
                notifyItemChanged(selectedItemPosition)
            }
        }
        @SuppressLint("ResourceAsColor")
        fun bind(category: String, position: Int) {
            binding.categoryTv.text = category

            if (position == selectedItemPosition) {
                binding.categoryTv.setTextColor(Color.WHITE)
                binding.category.setBackgroundResource(R.drawable.background_radius8_purple600)
            } else if (position == previousItemPosition) {
                binding.categoryTv.setTextColor(Color.parseColor("#7B79E8"))  // purple500
                binding.category.setBackgroundResource(R.drawable.background_radius8_purple200)
            } else {
                binding.categoryTv.setTextColor(Color.parseColor("#7B79E8"))  // purple500
                binding.category.setBackgroundResource(R.drawable.background_radius8_purple200)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rvAdapterCategory.ViewHolder {
        val view = RvItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: rvAdapterCategory.ViewHolder, position: Int) {
        holder.bind(categoryList[position], position)
    }

    companion object{
        const val DEFAULT_TAG_POSITION = 0
    }
}