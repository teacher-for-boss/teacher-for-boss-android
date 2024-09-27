package com.company.teacherforboss.presentation.ui.community.teacher_talk.body.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemTagBinding
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyActivity

class rvAdapterTag(private val tagList: List<String>, private val context: Context): RecyclerView.Adapter<rvAdapterTag.ViewHolder>() {

    class ViewHolder(binding: RvItemTagBinding):RecyclerView.ViewHolder(binding.root) {
        val tag: ConstraintLayout = binding.tagArea
        val tagName: TextView = binding.tagName

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tagName.text = tagList[position]

        when(context) {
            is TeacherTalkBodyActivity -> {
                if(position == 0) {
                    holder.tag.setBackgroundResource(R.drawable.background_radius4_purple600)
                    holder.tagName.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
            }
            is BossTalkBodyActivity -> {}
        }
    }

    override fun getItemCount(): Int {
        return tagList.size
    }
}