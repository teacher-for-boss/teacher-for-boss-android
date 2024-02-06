package com.example.teacherforboss.signup.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentLocationserviceMoreBinding
import com.example.teacherforboss.databinding.FragmentPersonalinfoMoreBinding
import com.example.teacherforboss.signup.SignupViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LocationServiceMoreFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentLocationserviceMoreBinding
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_locationservice_more,container,false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }
}