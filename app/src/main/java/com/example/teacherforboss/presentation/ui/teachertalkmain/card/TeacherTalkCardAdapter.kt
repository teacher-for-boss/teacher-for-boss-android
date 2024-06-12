package com.example.teacherforboss.presentation.ui.teachertalkmain.card

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemTeacherTalkCardBinding

class TeacherTalkCardAdapter(context: Context) :
    RecyclerView.Adapter<TeacherTalkMainCardViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var teacherTalkCardList: List<TeacherTalkCard> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeacherTalkMainCardViewHolder {
        val binding = ItemTeacherTalkCardBinding.inflate(inflater, parent, false)
        return TeacherTalkMainCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherTalkMainCardViewHolder, position: Int) {
        holder.onBind(teacherTalkCardList[position])
    }

    override fun getItemCount() = teacherTalkCardList.size

    fun setCardList(cardList: List<TeacherTalkCard>) {
        this.teacherTalkCardList = cardList.toList()
        notifyDataSetChanged()
    }
}
