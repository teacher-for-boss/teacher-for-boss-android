package com.example.teacherforboss.presentation.ui.auth.signup.teacher

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentAccountBinding
import com.example.teacherforboss.databinding.FragmentBusinessBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.signup.fragment.EmailFragment

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private val viewModel by activityViewModels<SignupViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_account,container, false)
        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        addListeners()
        setObserver()
        return binding.root
    }



    private fun addListeners(){
        val activity = activity as SignupActivity
        binding.btnNextSignup.setOnClickListener {
            activity.gotoNextFragment(EmailFragment())
        }
        binding.bank.setOnClickListener {
            val transaction=parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,BankFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }
    private fun checkFilled() {
        if (!viewModel._bank.value.isNullOrEmpty() &&
            !viewModel._accountNum.value.isNullOrEmpty() &&
            !viewModel._accountHoler.value.isNullOrEmpty())
            viewModel.enableNext.value = true
        else viewModel.enableNext.value = false
    }
    private fun setObserver(){
        val dataObserver = Observer<String>{ _ -> checkFilled() }
        viewModel._bank.observe(viewLifecycleOwner,dataObserver)
        viewModel._accountNum.observe(viewLifecycleOwner,dataObserver)
        viewModel._accountHoler.observe(viewLifecycleOwner,dataObserver)
    }


}