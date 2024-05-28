package com.example.teacherforboss.presentation.ui.auth.signup.basic

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentAgreementBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.boss.BossProfileFragment
import com.example.teacherforboss.presentation.ui.auth.signup.boss.TeacherProfileFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

//@AndroidEntryPoint
class AgreementFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAgreementBinding
    private val viewModel by activityViewModels<SignupViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_agreement,container,false)

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
            binding.locationServiceCheckbox, binding.benefitInformationCheckbox,
            binding.smsCheckbox, binding.emailCheckbox, binding.ageCheckbox
        )
        val vitalCheckboxes = arrayOf(
            binding.agreementCheckbox, binding.personalInformationCheckbox,
            binding.locationServiceCheckbox, binding.ageCheckbox
        )

        //위의 두 리스트 합친것
        val combinedList=agreementList.zip(otherCheckboxes)


        binding.allCheckbox.setOnClickListener {
            for(checkBox in otherCheckboxes) {
                checkBox.isChecked = binding.allCheckbox.isChecked
            }
        }

        binding.agreementCheckbox.setOnCheckedChangeListener { _, isChecked ->
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
        binding.personalInformationCheckbox.setOnCheckedChangeListener { _, isChecked ->
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
        binding.locationServiceCheckbox.setOnCheckedChangeListener { _, isChecked ->
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
        binding.ageCheckbox.setOnCheckedChangeListener { _, isChecked ->
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

        //회원가입 인증결과 수신
        viewModel.signupResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
                }
                is BaseResponse.Error->{
                    showToast("error:"+it.msg)
                }

                else -> {}
            }
        }



        binding.finishBtn.setOnClickListener {
            val activity=activity as SignupActivity
            //체크된 agreemet 확인
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

            // 화면 이동 시 다이얼로그 종료
            dismiss()
        }

        return binding.root
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