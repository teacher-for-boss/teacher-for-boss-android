package com.company.teacherforboss.presentation.ui.community.teacher_talk.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.company.teacherforboss.databinding.ItemSpinnerDropdownBinding
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ItemSpinnerBinding

class CustomAdapter(
    private val context: Context,
    private var items: Array<String>
) : BaseAdapter() {
    private var selectedItem: String? = null

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // spinner 닫혔을때 선택된 옵션
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemSpinnerBinding
        if (convertView == null) {
            binding = ItemSpinnerBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemSpinnerBinding
        }

        binding.tvSpinnerTitle.text = selectedItem ?: items[position]
        binding.ivDropdown.setImageResource(R.drawable.ic_dropdown)

        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemSpinnerDropdownBinding
        if (convertView == null) {
            binding = ItemSpinnerDropdownBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemSpinnerDropdownBinding
        }

        binding.tvSpinnerTitle.text = items[position]

        return binding.root
    }

    // TODO: 선택된 아이템 제거 후 드롭다운
//    fun updateItems(selected:String){
//        selectedItem=selected
//        items= items.filter { it!=selectedItem }.toTypedArray()
//        notifyDataSetChanged()
//    }
}
