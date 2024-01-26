package com.example.teacherforboss.presentation.ui.auth.signup.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentAgreementBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.presentation.ui.main.BeginActivity

class AgreementFragment : DialogFragment() {
    private lateinit var binding: FragmentAgreementBinding
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_agreement, container, false)

        binding.signupViewModel = viewModel
        binding.lifecycleOwner = this

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.allCheckBtn.setOnClickListener {
            val isAllChecked = binding.allCheckBtn.isChecked
            val otherRadisButtons = arrayOf(
                binding.btn1,
                binding.btn2,
                binding.btn3,
                binding.btn4,
                binding.btn5,
                binding.btn6,
                binding.btn7,
            )

            for (radioBUtton in otherRadisButtons) {
                radioBUtton.isChecked = isAllChecked
            }
        }
        binding.finishBtn.setOnClickListener {
            // 서버로 회원가입 api 보내기
            // 성공시 아래작업 진행
            val activity = activity as SignupActivity
            val intent = Intent(activity, BeginActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}
