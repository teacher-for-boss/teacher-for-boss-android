package com.company.teacherforboss.presentation.ui.mypage

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemAskPaymentBinding
import com.company.teacherforboss.presentation.model.AskPaymentItemData

class AskPaymentAdapter(
    private val items: List<AskPaymentItemData>,
    private val onSelectionChanged: (Boolean) -> Unit
) : RecyclerView.Adapter<AskPaymentAdapter.ItemViewHolder>() {

    private var selectedPosition = -1

    inner class ItemViewHolder(val binding: RvItemAskPaymentBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                // select 효과
                val previousSelectedPosition = selectedPosition
                if (selectedPosition == adapterPosition) { selectedPosition = -1 }
                else { selectedPosition = adapterPosition }

                onSelectionChanged(selectedPosition != -1)

                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
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
        holder.binding.paymentInfo.text = item.info
        holder.binding.paymentDiscountPercent.text = item.discountPercent
        holder.binding.paymentPrice.text = item.price
        if(item.discount.isNotEmpty()) {
            holder.binding.paymentDiscount.text = item.discount
            holder.binding.paymentDiscount.paintFlags = holder.binding.paymentDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        else {
            holder.binding.paymentDiscount.visibility = View.GONE
        }

        // 선택 여부에 따른 상태 처리
        holder.binding.root.isSelected = (position == selectedPosition)

        // 무료 이벤트 끝나면 지워도 되는 코드
        if(position > 0) {
            holder.binding.layoutPayment.isClickable = false
            holder.binding.layoutPayment.isEnabled = false

            holder.binding.layoutPaymentBox.setBackgroundResource(R.drawable.background_radius10_gray200)
            holder.binding.paymentDiscountPercent.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.Gray300))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}