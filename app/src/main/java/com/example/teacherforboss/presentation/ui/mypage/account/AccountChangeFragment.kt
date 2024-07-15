package com.example.teacherforboss.presentation.ui.mypage.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentAccountChangeBinding
import com.example.teacherforboss.presentation.ui.auth.signup.teacher.BankFragment
import com.example.teacherforboss.presentation.ui.mypage.account.AccountViewModel
import com.example.teacherforboss.presentation.ui.mypage.account.BankAccountFragment

class AccountChangeFragment : Fragment() {
    private var _binding: FragmentAccountChangeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<AccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountChangeBinding.inflate(inflater, container, false)

        addListeners()
        setObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chosenBank.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BankAccountFragment())
                .addToBackStack(null)
                .commit()
        }


    }

    private fun addListeners(){

        binding.chosenBank.setOnClickListener {
            val transaction=parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, BankFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }
    private fun checkFilled() {
        if (!viewModel._chosenBank.value.isNullOrEmpty() &&
            !viewModel._etInputAccount.value.isNullOrEmpty() &&
            !viewModel._etInputName.value.isNullOrEmpty())
            viewModel.enableNext.value = true
        else viewModel.enableNext.value = false
    }
    private fun setObserver(){
        val dataObserver = Observer<String>{ _ -> checkFilled() }
        viewModel._chosenBank.observe(viewLifecycleOwner,dataObserver)
        viewModel._etInputAccount.observe(viewLifecycleOwner,dataObserver)
        viewModel._etInputName.observe(viewLifecycleOwner,dataObserver)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}