package com.company.teacherforboss.presentation.ui.auth.signup.teacher

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.RvItemBankBinding
import com.company.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.presentation.ui.mypage.account.BankAccount

class rvAdapterBank(
    private val bankList: List<BankAccount>,
    private val signupViewModel: SignupViewModel,
    private val activity: SignupActivity,
    private val parentFragmentManager: FragmentManager,
    ): RecyclerView.Adapter<rvAdapterBank.ViewHolder>() {

    class ViewHolder(binding: RvItemBankBinding): RecyclerView.ViewHolder(binding.root) {
        val bankImage: ImageView = binding.bankImage
        val bankName: TextView = binding.bankName
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rvAdapterBank.ViewHolder {
        val view = RvItemBankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: rvAdapterBank.ViewHolder, position: Int) {
        val bank=bankList[position]

        holder.bankImage.setImageResource(bank.imageResId)
        holder.bankName.text = bank.name

        holder.itemView.setOnClickListener {
            signupViewModel._bank.value=bank.name
            parentFragmentManager.popBackStack()
        }
    }

    override fun getItemCount(): Int {
        return bankList.size
    }
}