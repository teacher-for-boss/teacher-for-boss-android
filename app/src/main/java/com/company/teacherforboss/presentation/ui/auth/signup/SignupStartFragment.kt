package com.company.teacherforboss.presentation.ui.auth.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentSignupStartBinding
import com.company.teacherforboss.presentation.ui.auth.signup.boss.BossProfileFragment
import com.company.teacherforboss.presentation.ui.auth.signup.teacher.BusinessInfoFragment
import com.company.teacherforboss.signup.fragment.EmailFragment
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIGNUP_DEFAULT
import com.company.teacherforboss.util.base.LocalDataSource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignupStartFragment : BindingFragment<FragmentSignupStartBinding>(R.layout.fragment_signup_start) {

    private val viewModel by activityViewModels<SignupViewModel>()
    @Inject lateinit var localDataSource: LocalDataSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupViewModel = viewModel
        binding.lifecycleOwner = this

        val activity = activity as SignupActivity
        val btn1 = binding.bossSelectTab
        val btn2 = binding.teacherSelectTab
        btn1.isSelected = true

        btn1.setOnClickListener {
            btn1.isSelected = true
            btn2.isSelected = false
        }

        btn2.setOnClickListener {
            btn1.isSelected = false
            btn2.isSelected = true
        }

        binding.nextBtn.setOnClickListener {
            val signupType = localDataSource.getSignupType()

            if (btn1.isSelected) {
                viewModel.setBossMode()
                viewModel.changeToBossPageSize()

                if (signupType != SIGNUP_DEFAULT) {
                    activity.gotoNextFragment(BossProfileFragment())
                } else {
                    activity.gotoNextFragment(EmailFragment())
                }
            } else {
                viewModel.setTeacherMode()
                viewModel.changeToTeacherPageSize()
                activity.gotoNextFragment(BusinessInfoFragment())
            }
        }
    }
}