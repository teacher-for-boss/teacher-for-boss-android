//package com.example.teacherforboss.presentation.ui.teachertalkmain.Category
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.teacherforboss.databinding.ItemTeacherTalkCategoryBinding
//
//class TeacherTalkCategoryAdpapter(context: Context) :RecyclerView.Adapter<TeacherTalkCategoryViewHolder>() {
//    private val inflater by lazy { LayoutInflater.from(context) }
//
//    private var teacherTalkCategoryList: List<TeacherTalkCategory> = emptyList()
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): TeacherTalkCategoryViewHolder {
//        val binding = ItemTeacherTalkCategoryBinding.inflate(inflater, parent, false)
//        return TeacherTalkCategoryViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: TeacherTalkCategoryViewHolder, position: Int) {
//        holder.onBind(teacherTalkCategoryList[position])
//    }
//
//    override fun getItemCount() = teacherTalkCategoryList.size
//
//    fun setTeacherTalkCategoryList(teacherTalkCategoryList: List<TeacherTalkCategory>) {
//        this.teacherTalkCategoryList = teacherTalkCategoryList.toList()
//        notifyDataSetChanged()
//    }
//}
//
//
