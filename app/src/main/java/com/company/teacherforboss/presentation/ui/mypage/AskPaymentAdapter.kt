package com.company.teacherforboss.presentation.ui.mypage

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.RvItemAskPaymentBinding
import com.company.teacherforboss.presentation.model.AskPaymentItemData

class AskPaymentAdapter(private val items: List<AskPaymentItemData>) : RecyclerView.Adapter<AskPaymentAdapter.ItemViewHolder>() {

    private var selectedPosition = -1

    inner class ItemViewHolder(val binding: RvItemAskPaymentBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                // select 효과
                val previousSelectedPosition = selectedPosition
                selectedPosition = adapterPosition

                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
            // 취소선 적용
            binding.paymentDiscount.paintFlags = binding.paymentDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // 뷰 바인딩 객체 생성
        val binding = RvItemAskPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        // 데이터 바인딩
        holder.binding.paymentNumber.text = item.number
        holder.binding.paymentDiscount.text = item.discount
        holder.binding.paymentPrice.text = item.price

        // 선택 여부에 따른 상태 처리
        holder.binding.root.isSelected = (position == selectedPosition)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}