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
import com.company.teacherforboss.domain.model.exchange.ExchangeListResponseEntity

class rvAdapterExchangeHistory(
    private val context: Context,
    private var ExchangeHistoryList: MutableList<ExchangeListResponseEntity.ExchangeEntity>
) : RecyclerView.Adapter<rvAdapterExchangeHistory.ViewHolder>() {
    inner class ViewHolder(private val binding: RvItemExchangeHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (exchangeEntity: ExchangeListResponseEntity.ExchangeEntity) {
            with(binding) {
                if(exchangeEntity.type == "exchange") {
                    rvExchangeHistoryName.text =
                        context.getString(R.string.exchange_history_exchange)
                }
                else {
                    rvExchangeHistoryName.text = context.getString(R.string.exchange_history_refund)
                }

                rvExchangeHistoryTp.text = context.getString(R.string.exchange_history_points, exchangeEntity.points)
                rvExchangeHistoryTime.text = exchangeEntity.time
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

    fun updateData(newExchangeList: List<ExchangeListResponseEntity.ExchangeEntity>) {
        ExchangeHistoryList = newExchangeList.toMutableList()
        notifyDataSetChanged()
    }

    fun addMoreData(newExchangeList: List<ExchangeListResponseEntity.ExchangeEntity>) {
        val currentSize = ExchangeHistoryList.size
        val newItemSize = newExchangeList.size
        if(newItemSize > 0) {
            ExchangeHistoryList.addAll(newExchangeList)
            notifyItemRangeInserted(currentSize, newItemSize)
        }
    }
}