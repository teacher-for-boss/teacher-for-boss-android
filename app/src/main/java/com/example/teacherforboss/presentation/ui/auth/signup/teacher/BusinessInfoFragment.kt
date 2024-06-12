package com.example.teacherforboss.presentation.ui.auth.signup.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentBusinessInfoBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel

class BusinessInfoFragment : Fragment(){
    private val viewModel by activityViewModels<SignupViewModel>()
    private lateinit var binding: FragmentBusinessInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_business_info, container, false)
        addListeners()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun addListeners() {
        binding.btnNextSignup.setOnClickListener {
            val activity = activity as SignupActivity
            activity.gotoNextFragment(BusinessFragment())

        }


    }
}