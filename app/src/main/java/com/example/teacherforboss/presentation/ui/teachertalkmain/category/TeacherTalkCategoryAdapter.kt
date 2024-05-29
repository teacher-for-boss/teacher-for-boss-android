package com.example.teacherforboss.presentation.ui.teachertalkmain.category

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.teacherforboss.databinding.ItemTeacherTalkCategoryBinding

class TeacherTalkCategoryAdapter(context: Context) : RecyclerView.Adapter<TeacherTalkCategoryViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var TeacherTalkCategoryList: List<TeacherTalkCategory> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): TeacherTalkCategoryViewHolder {
        val binding = ItemTeacherTalkCategoryBinding.inflate(inflater, parent, false)
        return TeacherTalkCategoryViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TeacherTalkCategoryViewHolder, position: Int) {
        holder.onBind(TeacherTalkCategoryList[position])
    }
    override fun getItemCount() = TeacherTalkCategoryList.size

    // 임시 리스트에 준비해둔 가짜 리스트를 연결하는 함수
    fun setTeacherTalkCategoryList(TeacherTalkCategoryList: List<TeacherTalkCategory>) {
        this.TeacherTalkCategoryList = TeacherTalkCategoryList.toList()
        notifyDataSetChanged()
    }

}
