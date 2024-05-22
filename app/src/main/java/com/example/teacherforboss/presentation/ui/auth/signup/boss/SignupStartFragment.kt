package com.example.teacherforboss.presentation.ui.auth.signup.boss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentSignupStartBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel



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
            if(btn1.isSelected){
                activity.gotoNextFragment(BossProfileFragment())
            }
            else{
                activity.gotoNextFragment(TeacherProfileFragment())

            }
        }

        return binding.root

    }
}