package com.company.teacherforboss.presentation.ui.mypage.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentBankAccountBinding

class BankAccountFragment : Fragment() {
    private val viewModel by activityViewModels<AccountViewModel>()
    lateinit var binding: FragmentBankAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bank_account, container, false)

        val bankList = listOf(
            BankAccount(R.drawable.bank_toss, "토스뱅크"),
            BankAccount(R.drawable.bank_kakao, "카카오뱅크"),
            BankAccount(R.drawable.bank_nonghyup, "NH농협"),
            BankAccount(R.drawable.bank_kookmin, "KB국민은행"),
            BankAccount(R.drawable.bank_shinhan, "신한은행"),
            BankAccount(R.drawable.bank_woori, "우리은행"),
            BankAccount(R.drawable.bank_ibk, "IBK기업은행"),
            BankAccount(R.drawable.bank_hana, "하나은행"),
            BankAccount(R.drawable.bank_k, "케이뱅크"),
            BankAccount(R.drawable.bank_busan, "부산은행"),
            BankAccount(R.drawable.bank_daegu, "대구은행"),
            BankAccount(R.drawable.bank_kdb, "KDB산업은행"),
            BankAccount(R.drawable.bank_sc, "SC제일은행"),
            BankAccount(R.drawable.bank_suhyup, "수협"),
            BankAccount(R.drawable.bank_gwangju, "광주"),
            BankAccount(R.drawable.bank_busan, "경남"),
            BankAccount(R.drawable.bank_gwangju, "전북"),
            BankAccount(R.drawable.bank_shinhan, "제주"),
            BankAccount(R.drawable.bank_citi, "한국씨티"),
            BankAccount(R.drawable.bank_post, "우체국"),
            BankAccount(R.drawable.bank_saving, "저축은행")
        )

        binding.rvBank.adapter = rvAdapterBankAccount(bankList, viewModel, parentFragmentManager)
        binding.rvBank.layoutManager = GridLayoutManager(requireContext(), 3)

        return binding.root
    }
}