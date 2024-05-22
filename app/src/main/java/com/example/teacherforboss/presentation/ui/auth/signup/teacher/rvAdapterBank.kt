package com.example.teacherforboss.presentation.ui.auth.signup.teacher

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.RvItemBankBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel

class rvAdapterBank(
    private val bankList: List<Bank>,
    private val signupViewModel: SignupViewModel,
    private val activity: SignupActivity,


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
            activity.gotoNextFragment(AccountFragment())
        }
    }

    override fun getItemCount(): Int {
        return bankList.size
    }
}