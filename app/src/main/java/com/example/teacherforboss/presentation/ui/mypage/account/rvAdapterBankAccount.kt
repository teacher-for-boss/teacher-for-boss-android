package com.example.teacherforboss.presentation.ui.mypage.account

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.RvItemBankBinding

class rvAdapterBankAccount(
    private val bankList: List<BankAccount>,
    private val accountViewModel: AccountViewModel,
    private val parentFragmentManager: FragmentManager
) : RecyclerView.Adapter<rvAdapterBankAccount.ViewHolder>() {

    class ViewHolder(binding: RvItemBankBinding): RecyclerView.ViewHolder(binding.root) {
        val bankImage: ImageView = binding.bankImage
        val bankName: TextView = binding.bankName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemBankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bank = bankList[position]

        holder.bankImage.setImageResource(bank.imageResId)
        holder.bankName.text = bank.name

        holder.itemView.setOnClickListener {
            accountViewModel._chosenBank.value = bank.name
            parentFragmentManager.popBackStack()
        }
    }

    override fun getItemCount(): Int {
        return bankList.size
    }
}