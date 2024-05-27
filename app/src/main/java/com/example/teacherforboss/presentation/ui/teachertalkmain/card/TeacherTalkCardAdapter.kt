package com.example.teacherforboss.presentation.ui.teachertalkmain.card

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemTeacherTalkCardBinding
import com.example.teacherforboss.util.view.ItemDiffCallback

class TeacherTalkCardAdapter(context: Context) : ListAdapter<TeacherTalkCard,RecyclerView.ViewHolder>(
    ItemDiffCallback<TeacherTalkCard>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old.question == new.question }
    )
) {
    private val inflater by lazy { LayoutInflater.from(context) }

    // 임시의 빈 리스트
    private var teacherTalkCardList: List<TeacherTalkCard> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTeacherTalkCardBinding.inflate(inflater, parent, false)
        return TeacherTalkMainCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            holder is TeacherTalkMainCardViewHolder -> {
                holder.onBind(teacherTalkCardList[position])
            }
        }
    }



    // 임시 리스트에 준비해둔 가짜 리스트를 연결하는 함수
    fun setTeacherTalkCardList(friendList: List<TeacherTalkCard>) {
        this.teacherTalkCardList = friendList.toList()
        notifyDataSetChanged()
    }
}