package com.company.teacherforboss.presentation.ui.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemAskPaymentHistoryBinding
import com.company.teacherforboss.databinding.RvItemCommentBossBinding
import com.company.teacherforboss.presentation.ui.mypage.exchange.AskPaymentHistoryItem

class rvAdapterAskPaymentHistory(
    private val context: Context,
    private val AskPaymentHistoryList: List<AskPaymentHistoryItem>
) : RecyclerView.Adapter<rvAdapterAskPaymentHistory.ViewHolder>() {
    inner class ViewHolder(private val binding: RvItemAskPaymentHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (/*history:ExchangeHistoryEntity*/ history:AskPaymentHistoryItem){
            binding.rvAskPaymentHistoryTime.text = history.time
            when (history.type){
                "pay" -> {
                    binding.apply {
                        rvAskPaymentHistoryName.text = context.getString(R.string.ask_payment_history_pay)
                        rvAskPaymentHistoryQticket.text = "+${history.questionTicketCount}개"
                    }
                }
                "refund" -> {
                    binding.apply {
                        rvAskPaymentHistoryName.text = context.getString(R.string.ask_payment_history_refund)
                        rvAskPaymentHistoryQticket.text = "-${history.questionTicketCount}개"
                    }

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemAskPaymentHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return AskPaymentHistoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(AskPaymentHistoryList[position])
    }


}