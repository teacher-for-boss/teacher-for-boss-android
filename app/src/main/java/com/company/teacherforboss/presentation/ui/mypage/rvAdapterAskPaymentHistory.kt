package com.company.teacherforboss.presentation.ui.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.presentation.ui.mypage.exchange.AskPaymentHistoryItem

class rvAdapterAskPaymentHistory(
    private val context: Context,
    private val AskPaymentHistoryList: List<AskPaymentHistoryItem>
) : RecyclerView.Adapter<rvAdapterAskPaymentHistory.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.rv_ask_payment_history_name)
        val QticketText: TextView = itemView.findViewById(R.id.rv_ask_payment_history_Qticket)
        val timeText: TextView = itemView.findViewById(R.id.rv_ask_payment_history_time)

        fun bind (/*history:ExchangeHistoryEntity*/ history:AskPaymentHistoryItem){
            timeText.text = history.time
            when (history.type){
                "pay" -> {
                    nameText.text = context.getString(R.string.ask_payment_history_pay)
                    QticketText.text = "+${history.questionTicketCount}개"
                }
                "refund" -> {
                    nameText.text = context.getString(R.string.ask_payment_history_refund)
                    QticketText.text = "-${history.questionTicketCount}개"

                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_ask_payment_history, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return AskPaymentHistoryList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(AskPaymentHistoryList[position])
    }


}