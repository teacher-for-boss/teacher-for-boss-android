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
import com.company.teacherforboss.util.base.LocalDataSource


class SignupStartFragment : Fragment() {

    private lateinit var binding: FragmentSignupStartBinding
    private val viewModel by activityViewModels<SignupViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup_start, container, false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        val activity=activity as SignupActivity
        var btn1 = binding.bossSelectTab
        var btn2 = binding.teacherSelectTab
        btn1.isSelected = true

        btn1.setOnClickListener(){
            btn1.isSelected = true
            btn2.isSelected = false

        }
        btn2.setOnClickListener(){
            btn1.isSelected = false
            btn2.isSelected = true
        }
        binding.nextBtn.setOnClickListener(){
            val signupType= LocalDataSource.getSignupType(requireContext())

            if(btn1.isSelected){
                viewModel.setBossMode()
                viewModel.changeToBossPageSize()
                // 소셜로 회원가입 중일때
                if (signupType != SIGNUP_DEFAULT) activity.gotoNextFragment(BossProfileFragment())
                else  activity.gotoNextFragment(EmailFragment())
            }
            else{
                viewModel.setTeacherMode()
                viewModel.changeToTeacherPageSize()
                activity.gotoNextFragment(BusinessInfoFragment())
//                activity.gotoNextFragment(TeacherProfileFragment())
            }
        }

        return binding.root

    }
    companion object{
        const val SIGNUP_TYPE="SIGNUP_TYPE"
        const val SIGNUP_DEFAULT="SIGNUP_DEFAULT"
    }
}