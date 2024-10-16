package com.company.teacherforboss.presentation.ui.auth.signup.teacher

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentBankBinding
import com.company.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.presentation.ui.mypage.account.BankAccount
import com.company.teacherforboss.util.base.BindingFragment

class BankFragment : BindingFragment<FragmentBankBinding>(R.layout.fragment_bank) {
    private val viewModel by activityViewModels<SignupViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val activity = activity as SignupActivity

        binding.rvBank.adapter = rvAdapterBank(bankList,viewModel,activity,parentFragmentManager)
        binding.rvBank.layoutManager = GridLayoutManager(requireContext(), 3)
    }
}