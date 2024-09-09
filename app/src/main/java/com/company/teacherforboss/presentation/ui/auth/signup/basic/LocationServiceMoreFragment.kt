package com.company.teacherforboss.presentation.ui.auth.signup.basic

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentLocationserviceMoreBinding
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.util.base.BindingDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

//@AndroidEntryPoint
class LocationServiceMoreFragment : BindingDialogFragment<FragmentLocationserviceMoreBinding>(R.layout.fragment_locationservice_more) {
    private val viewModel by activityViewModels<SignupViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }
}