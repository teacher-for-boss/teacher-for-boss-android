package com.example.teacherforboss.presentation.ui.auth.signup.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentAgreementBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.fragment.AgreementMoreFragment
import com.example.teacherforboss.presentation.ui.auth.signup.fragment.LocationServiceMoreFragment
import com.example.teacherforboss.presentation.ui.auth.signup.fragment.PersonalInfoMoreFragment
import com.example.teacherforboss.presentation.ui.main.BeginActivity
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

        //체크박스 설정
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

        val activity=activity as SignupActivity
        //약관 전체보기
        binding.agreementMore.setOnClickListener {
            val bottomSheetDialog= AgreementMoreFragment()
            //bottomSheetDialog.setStyle(DialogFragment.STYLE_NORMAL, AppBottomSheetDialogTheme)
            bottomSheetDialog.show(activity.supportFragmentManager,"agreement")
        }
        binding.personalInformationMore.setOnClickListener {
            val bottomSheetDialog= PersonalInfoMoreFragment()
            //bottomSheetDialog.setStyle(DialogFragment.STYLE_NORMAL, AppBottomSheetDialogTheme)
            bottomSheetDialog.show(activity.supportFragmentManager,"agreement")
        }
        binding.locationServiceMore.setOnClickListener {
            val bottomSheetDialog= LocationServiceMoreFragment()
            //bottomSheetDialog.setStyle(DialogFragment.STYLE_NORMAL, AppBottomSheetDialogTheme)
            bottomSheetDialog.show(activity.supportFragmentManager,"agreement")
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