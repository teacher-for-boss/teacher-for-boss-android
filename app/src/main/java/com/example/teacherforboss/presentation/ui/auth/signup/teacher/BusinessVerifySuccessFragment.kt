package com.example.teacherforboss.presentation.ui.auth.signup.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teacherforboss.databinding.FragmentBusinessVerifySuccessBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity

class BusinessVerifySuccessFragment : Fragment() {
    private lateinit var binding:FragmentBusinessVerifySuccessBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentBusinessVerifySuccessBinding.inflate(inflater, container, false)

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