package com.example.teacherforboss.presentation.ui.community.teacher_talk.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.teacherforboss.databinding.ItemSpinnerDropdownBinding
import android.widget.TextView
import com.example.teacherforboss.R

class CustomAdapter(
    private val context: Context,
    private val items: Array<String>
) : BaseAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemSpinnerDropdownBinding
        if (convertView == null) {
            binding = ItemSpinnerDropdownBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemSpinnerDropdownBinding
        }

        binding.tvDropdown.text = items[position]
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

        binding.tvDropdown.text = items[position]
        binding.tvDropdown.gravity = android.view.Gravity.CENTER
        binding.ivDropdown.visibility = View.GONE

        return binding.root
    }
}
