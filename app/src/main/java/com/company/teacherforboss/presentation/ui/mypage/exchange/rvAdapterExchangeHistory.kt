package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R

class rvAdapterExchangeHistory(
    private val context: Context,
    private val ExchangeHistoryList: List<ExchangeHistoryItem>
) : RecyclerView.Adapter<rvAdapterExchangeHistory.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.rv_exchange_history_name)
        val tpText: TextView = itemView.findViewById(R.id.rv_exchange_history_tp)
        val timeText: TextView = itemView.findViewById(R.id.rv_exchange_history_time)

        fun bind (/*history:ExchangeHistoryEntity*/ history:ExchangeHistoryItem){
            timeText.text = history.time
            when (history.type){
                "exchange" -> {
                    nameText.text = context.getString(R.string.exchange_history_exchange)
                    tpText.text = "+${history.points}TP"
                }
                "refund" -> {
                    nameText.text = context.getString(R.string.exchange_history_refund)
                    tpText.text = "-${history.points}TP"

                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_exchange_history, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ExchangeHistoryList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(ExchangeHistoryList[position])
    }


}