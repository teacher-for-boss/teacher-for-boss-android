package com.example.teacherforboss.presentation.ui.teachertalkmain.card

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemTeacherTalkCardBinding

class TeacherTalkCardAdapter(context: Context) :
    RecyclerView.Adapter<TeacherTalkMainCardViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var teacherTalkCardList: MutableList<TeacherTalkCard> = mutableListOf()
    private var allTeacherTalkCard: List<TeacherTalkCard> = emptyList()

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

    override fun getItemCount(): Int {
        return teacherTalkCardList.size
    }

    fun setCardList(cardList: List<TeacherTalkCard>) {
        this.allTeacherTalkCard = cardList
        this.teacherTalkCardList = allTeacherTalkCard.take(10).toMutableList()
        notifyDataSetChanged()
    }

    fun addMoreCards() {
        val currentSize = teacherTalkCardList.size
        val nextSize = minOf(currentSize + 10, allTeacherTalkCard.size)
        if (currentSize < nextSize) {
            teacherTalkCardList.addAll(allTeacherTalkCard.subList(currentSize, nextSize))
            notifyItemRangeInserted(currentSize, nextSize - currentSize)
        }
    }
}
