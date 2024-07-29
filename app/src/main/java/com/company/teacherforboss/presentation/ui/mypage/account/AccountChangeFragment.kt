package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentAccountChangeBinding
import com.company.teacherforboss.domain.model.payment.BankAccountResponseEntity
import com.company.teacherforboss.presentation.ui.mypage.account.AccountViewModel
import com.company.teacherforboss.presentation.ui.mypage.account.BankAccountFragment
import com.company.teacherforboss.util.view.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AccountChangeFragment : Fragment() {

    private var _binding: FragmentAccountChangeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<AccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountChangeBinding.inflate(inflater, container, false)
        binding.accountViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserProfile()
        collectData()


        binding.chosenBank.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BankAccountFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnComplete.setOnClickListener {
            val intent = Intent(requireActivity(), ExchangeActivity::class.java).apply {
                putExtra("startWithFragment2", true)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }
    private fun getUserProfile(){
        viewModel.getUserProfile()
    }
    private fun collectData() {
        viewModel.bankAccountInfoState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { bankAccountInfoState ->
                when (bankAccountInfoState) {
                    is UiState.Success -> {
                        val data = bankAccountInfoState.data
                        initLayout(data)
                    }
                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    private fun initLayout(bankAccount: BankAccountResponseEntity){
        viewModel.apply{
            setChosenBank(bankAccount.bank)
            setInputAccount(bankAccount.accountHolder)
            setInputName(bankAccount.accountNumber)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}