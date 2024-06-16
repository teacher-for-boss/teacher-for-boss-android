package com.example.teacherforboss.presentation.ui.community.boss_talk.main.card

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemBossTalkCardBinding
import com.example.teacherforboss.domain.model.community.PostEntity

class BossTalkMainCardAdapter(context: Context) :
    RecyclerView.Adapter<BossTalkMainCardViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var bossTalkCardList: List<PostEntity> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BossTalkMainCardViewHolder {
        val binding = ItemBossTalkCardBinding.inflate(inflater, parent, false)
        return BossTalkMainCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BossTalkMainCardViewHolder, position: Int) {
        holder.onBind(bossTalkCardList[position])
    }

    override fun getItemCount() = bossTalkCardList.size

    fun setCardList(cardList: List<PostEntity>) {
        this.bossTalkCardList = cardList.toList()
        notifyDataSetChanged()
    }
}
