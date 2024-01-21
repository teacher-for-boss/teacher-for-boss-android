package com.example.teacherforboss.signup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentEmailBinding
import com.example.teacherforboss.databinding.FragmentGenderBirthBinding
import com.example.teacherforboss.signup.SignupViewModel


class GenderBirthFragment : Fragment() {
    private lateinit var binding: FragmentGenderBirthBinding
    private val viewModel: SignupViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_gender_birth,container,false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        //gender birth 입력값 받아서 viewmodel에 저장 추가하기

        // Inflate the layout for this fragment
        return binding.root
    }


}