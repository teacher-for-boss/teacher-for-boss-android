package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemAskPaymentHistoryBinding
import com.company.teacherforboss.databinding.RvItemExchangeHistoryBinding

class rvAdapterExchangeHistory(
    private val context: Context,
    private val ExchangeHistoryList: List<ExchangeHistoryItem>
) : RecyclerView.Adapter<rvAdapterExchangeHistory.ViewHolder>() {
    inner class ViewHolder(private val binding: RvItemExchangeHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (/*history:ExchangeHistoryEntity*/ history:ExchangeHistoryItem){
            binding.rvExchangeHistoryTime.text = history.time
            when (history.type){
                "exchange" -> {
                    binding.apply {
                        rvExchangeHistoryName.text = context.getString(R.string.exchange_history_exchange)
                        rvExchangeHistoryTp.text = "+${history.points}TP"
                    }
                }
                "refund" -> {
                    binding.apply {
                        rvExchangeHistoryName.text = context.getString(R.string.exchange_history_refund)
                        rvExchangeHistoryTp.text = "-${history.points}TP"
                    }

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemExchangeHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ExchangeHistoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ExchangeHistoryList[position])
    }


}