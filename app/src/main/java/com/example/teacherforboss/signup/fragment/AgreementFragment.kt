package com.example.teacherforboss.signup.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.teacherforboss.BeginActivity
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentAgreementBinding
import com.example.teacherforboss.databinding.FragmentEmailBinding
import com.example.teacherforboss.login.LoginActivity
import com.example.teacherforboss.signup.SignupActivity
import com.example.teacherforboss.signup.SignupViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AgreementFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAgreementBinding
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_agreement,container,false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //전체 동의
        binding.allCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val otherCheckboxs = arrayOf(
                    binding.agreementCheckbox, binding.personalInformationCheckbox,
                    binding.locationServiceCheckbox, binding.benefitInformationCheckbox,
                    binding.smsCheckbox, binding.emailCheckbox, binding.ageCheckbox
                )

                for(checkBox in otherCheckboxs) {
                    checkBox.isChecked = true
                }
            }
        }
        //혜택정보 수신 동의
        var benefitInfo = binding.benefitInformationCheckbox
        var sms = binding.smsCheckbox
        var email = binding.emailCheckbox

        benefitInfo.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                if(sms.isChecked || email.isChecked) {}
                else {
                    sms.isChecked = true
                    email.isChecked = true
                }
            } else {
                sms.isChecked = false
                email.isChecked = false
            }
        }

        sms.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                benefitInfo.isChecked = true
            }
            else {
                if (!email.isChecked) benefitInfo.isChecked = false
            }
        }

        email.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                benefitInfo.isChecked = true
            }
            else {
                if (!sms.isChecked) benefitInfo.isChecked = false
            }
        }


        binding.finishBtn.setOnClickListener {
            //서버로 회원가입 api 보내기
            //성공시 아래작업 진행
            val activity=activity as SignupActivity
            val intent = Intent(activity, BeginActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}