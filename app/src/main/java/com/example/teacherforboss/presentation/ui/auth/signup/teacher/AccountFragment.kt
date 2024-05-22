package com.example.teacherforboss.presentation.ui.auth.signup.teacher

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
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

        addListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bank.text=viewModel.bank.value
    }


    private fun addListeners(){
        val activity = activity as SignupActivity
        binding.btnNextSignup.setOnClickListener {
            activity.gotoNextFragment(EmailFragment())
        }
        binding.bank.setOnClickListener {
            activity.gotoNextFragment(BankFragment())
        }

    }


}