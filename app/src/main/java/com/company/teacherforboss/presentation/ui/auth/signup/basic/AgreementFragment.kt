package com.company.teacherforboss.presentation.ui.auth.signup.basic

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentAgreementBinding
import com.company.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.presentation.ui.auth.signup.boss.BossProfileFragment
import com.company.teacherforboss.presentation.ui.auth.signup.boss.TeacherProfileFragment
import com.company.teacherforboss.util.base.BindingDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgreementFragment : BindingDialogFragment<FragmentAgreementBinding>(R.layout.fragment_agreement) {
    private val viewModel by activityViewModels<SignupViewModel>()

    override fun onStart() {
        super.onStart()

        dialog?.let {
            val params = it.window?.attributes
            params?.width = ViewGroup.LayoutParams.MATCH_PARENT
            params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            it.window?.attributes = params
            it.window?.setGravity(Gravity.BOTTOM) // 화면 하단에 붙이기
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //체크박스 설정

        //agreement 체크 여부 저장
        //for viewmodel
        var agreementList= mutableListOf <MutableLiveData<String>>(
            viewModel.agreementUsage,
            viewModel.agreementInfo,
            viewModel.agreementLocation,
            viewModel.agreementSms,
            viewModel.agreementEmail,
            viewModel.agreementAge,
        )

        //for xml data bidning
        val otherCheckboxes = arrayOf(
            binding.agreementCheckbox, binding.personalInformationCheckbox,
            binding.locationServiceCheckbox,
            binding.smsCheckbox, binding.emailCheckbox, binding.ageCheckbox
        )
        val vitalCheckboxes = arrayOf(
            binding.agreementCheckbox, binding.personalInformationCheckbox,
            binding.locationServiceCheckbox, binding.ageCheckbox
        )

        //위의 두 리스트 합친것
        val combinedList=agreementList.zip(otherCheckboxes)

        with(binding) {
            allCheckbox.setOnClickListener {
                for(checkBox in otherCheckboxes) {
                    checkBox.isChecked = binding.allCheckbox.isChecked
                }
            }
            agreementCheckbox.setOnCheckedChangeListener { _, isChecked ->
                binding.finishBtn.isEnabled = isVitalAgree(vitalCheckboxes)
                if(!isChecked) {
                    binding.allCheckbox.isChecked = false
                }
                else {
                    if(isAllAgree(otherCheckboxes)) {
                        binding.allCheckbox.isChecked = true
                    }
                }
            }
            personalInformationCheckbox.setOnCheckedChangeListener { _, isChecked ->
                binding.finishBtn.isEnabled = isVitalAgree(vitalCheckboxes)
                if(!isChecked) {
                    binding.allCheckbox.isChecked = false
                }
                else {
                    if(isAllAgree(otherCheckboxes)) {
                        binding.allCheckbox.isChecked = true
                    }
                }
            }
            locationServiceCheckbox.setOnCheckedChangeListener { _, isChecked ->
                binding.finishBtn.isEnabled = isVitalAgree(vitalCheckboxes)
                if(!isChecked) {
                    binding.allCheckbox.isChecked = false
                }
                else {
                    if(isAllAgree(otherCheckboxes)) {
                        binding.allCheckbox.isChecked = true
                    }
                }
            }
            ageCheckbox.setOnCheckedChangeListener { _, isChecked ->
                binding.finishBtn.isEnabled = isVitalAgree(vitalCheckboxes)
                if(!isChecked) {
                    binding.allCheckbox.isChecked = false
                }
                else {
                    if(isAllAgree(otherCheckboxes)) {
                        binding.allCheckbox.isChecked = true
                    }
                }
            }
        }

        //혜택정보 수신 동의
        var benefitInfo = binding.benefitInformationCheckbox
        var sms = binding.smsCheckbox
        var email = binding.emailCheckbox
        benefitInfo.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                if(sms.isChecked || email.isChecked) {
                    if(isAllAgree(otherCheckboxes)) {
                        binding.allCheckbox.isChecked = true
                    }
                }
                else {
                    sms.isChecked = true
                    email.isChecked = true
                }
            } else {
                sms.isChecked = false
                email.isChecked = false
                binding.allCheckbox.isChecked = false
            }
        }
        sms.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                benefitInfo.isChecked = true
                if(isAllAgree(otherCheckboxes)) {
                    binding.allCheckbox.isChecked = true
                }
            }
            else {
                binding.allCheckbox.isChecked = false
                if (!email.isChecked) benefitInfo.isChecked = false
            }
        }
        email.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                benefitInfo.isChecked = true
                if(isAllAgree(otherCheckboxes)) {
                    binding.allCheckbox.isChecked = true
                }
            }
            else {
                binding.allCheckbox.isChecked = false
                if (!sms.isChecked) benefitInfo.isChecked = false
            }
        }

        val activity=activity as SignupActivity
        binding.agreementMore.setOnClickListener {
            val bottomSheetDialog= AgreementMoreFragment()
            bottomSheetDialog.show(activity.supportFragmentManager,"agreement")
        }
        binding.personalInformationMore.setOnClickListener {
            val bottomSheetDialog= PersonalInfoMoreFragment()
            bottomSheetDialog.show(activity.supportFragmentManager,"agreement")
        }
        binding.locationServiceMore.setOnClickListener {
            val bottomSheetDialog= LocationServiceMoreFragment()
            bottomSheetDialog.show(activity.supportFragmentManager,"agreement")
        }

        binding.finishBtn.setOnClickListener {
            val activity=activity as SignupActivity
            combinedList.forEach{(livedata,checkbox)->
                if(checkbox.isChecked==true){
                    livedata.value="T"
                }
            }

            if(viewModel.role.value==1){
                activity.gotoNextFragment(BossProfileFragment())

            }
            else if(viewModel.role.value==2){
                activity.gotoNextFragment(TeacherProfileFragment())
            }
            dismiss()
        }

    }
    fun isAllAgree(otherCheckboxes: Array<CheckBox>): Boolean {
        var isAgree = true
        for(checkBox in otherCheckboxes) {
            if(!checkBox.isChecked) isAgree = false
        }
        return isAgree
    }
    fun isVitalAgree(vitalCheckboxes: Array<CheckBox>): Boolean {
        var isAgree = true
        for(checkBox in vitalCheckboxes) {
            if(!checkBox.isChecked) isAgree = false
        }
        return isAgree
    }

    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }
}