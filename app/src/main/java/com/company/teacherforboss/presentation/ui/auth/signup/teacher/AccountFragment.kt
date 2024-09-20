package com.company.teacherforboss.presentation.ui.auth.signup.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentAccountBinding
import com.company.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.presentation.ui.auth.signup.boss.TeacherProfileFragment
import com.company.teacherforboss.signup.fragment.EmailFragment
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIGNUP_DEFAULT
import com.company.teacherforboss.util.base.LocalDataSource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : BindingFragment<FragmentAccountBinding>(R.layout.fragment_account) {
    private val viewModel by activityViewModels<SignupViewModel>()
    @Inject
    lateinit var localDataSource: LocalDataSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        addListeners()
        setObserver()
    }



    private fun addListeners(){
        val activity = activity as SignupActivity
        val signupType= localDataSource.getSignupType()

        with(binding) {
            btnNextSignup.setOnClickListener {
                // 소셜로그인으로 회원가입 시
                if (signupType!= SIGNUP_DEFAULT) activity.gotoNextFragment(TeacherProfileFragment())
                else  activity.gotoNextFragment(EmailFragment())
            }
            bank.setOnClickListener {
                val transaction=parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container,BankFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }
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
        with(viewModel){
            _bank.observe(viewLifecycleOwner,dataObserver)
            _accountNum.observe(viewLifecycleOwner,dataObserver)
            _accountHoler.observe(viewLifecycleOwner,dataObserver)
        }
    }
}