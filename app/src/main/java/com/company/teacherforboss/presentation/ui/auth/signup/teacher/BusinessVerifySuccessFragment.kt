package com.company.teacherforboss.presentation.ui.auth.signup.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentBusinessVerifySuccessBinding
import com.company.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.util.base.BindingFragment

class BusinessVerifySuccessFragment : BindingFragment<FragmentBusinessVerifySuccessBinding>(R.layout.fragment_business_verify_success) {
    private val viewModel by activityViewModels<SignupViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addListeners()

        return binding.root
    }

    private fun addListeners(){
        binding.btnNextSignup.setOnClickListener {
            val activity = activity as SignupActivity
            activity.gotoNextFragment(AccountFragment())
        }
    }
}