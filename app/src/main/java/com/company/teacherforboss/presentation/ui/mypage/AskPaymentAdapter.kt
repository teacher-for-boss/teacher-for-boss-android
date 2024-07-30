package com.company.teacherforboss.presentation.ui.mypage

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.presentation.model.AskPaymentItemData

class AskPaymentAdapter(private val items: List<AskPaymentItemData>) : RecyclerView.Adapter<AskPaymentAdapter.ItemViewHolder>() {

    private var selectedPosition = -1

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberText: TextView = itemView.findViewById(R.id.payment_number)
        val discountText: TextView = itemView.findViewById(R.id.payment_discount)
        val priceText: TextView = itemView.findViewById(R.id.payment_price)

        init {
            itemView.setOnClickListener {
                // select 효과
                val previousSelectedPosition = selectedPosition
                selectedPosition = adapterPosition

                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
            //취소선
            discountText.paintFlags = discountText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_ask_payment, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        //프래그먼트 내 데이터 클래스 구현되어있음
        holder.numberText.text = item.number
        holder.discountText.text = item.discount
        holder.priceText.text = item.price

        holder.itemView.isSelected = (position == selectedPosition)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}