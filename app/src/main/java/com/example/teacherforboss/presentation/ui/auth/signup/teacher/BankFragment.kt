package com.example.teacherforboss.presentation.ui.auth.signup.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentBankBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel

class BankFragment : Fragment() {
    private val viewModel by activityViewModels<SignupViewModel>()
    lateinit var binding: FragmentBankBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bank, container, false)

        val bankList = listOf(
            Bank(R.drawable.bank_toss, "토스뱅크"),
            Bank(R.drawable.bank_kakao, "카카오뱅크"),
            Bank(R.drawable.bank_nonghyup, "NH농협"),
            Bank(R.drawable.bank_kookmin, "KB국민은행"),
            Bank(R.drawable.bank_shinhan, "신한은행"),
            Bank(R.drawable.bank_woori, "우리은행"),
            Bank(R.drawable.bank_ibk, "IBK기업은행"),
            Bank(R.drawable.bank_hana, "하나은행"),
            Bank(R.drawable.bank_k,"케이뱅크"),
            Bank(R.drawable.bank_busan, "부산은행"),
            Bank(R.drawable.bank_daegu, "대구은행"),
            Bank(R.drawable.bank_kdb, "KDB산업은행"),
            Bank(R.drawable.bank_sc, "SC제일은행"),
            Bank(R.drawable.bank_suhyup, "수협"),
            Bank(R.drawable.bank_gwangju, "광주")
        )
        val activity = activity as SignupActivity

        binding.rvBank.adapter = rvAdapterBank(bankList,viewModel,activity,parentFragmentManager)
        binding.rvBank.layoutManager = GridLayoutManager(requireContext(), 3)

        return binding.root
    }
}