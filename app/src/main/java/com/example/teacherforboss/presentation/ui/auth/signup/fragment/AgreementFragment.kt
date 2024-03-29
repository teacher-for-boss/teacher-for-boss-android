package com.example.teacherforboss.presentation.ui.auth.signup.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentAgreementBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.fragment.AgreementMoreFragment
import com.example.teacherforboss.presentation.ui.auth.signup.fragment.LocationServiceMoreFragment
import com.example.teacherforboss.presentation.ui.auth.signup.fragment.PersonalInfoMoreFragment
import com.example.teacherforboss.presentation.ui.main.BeginActivity
import com.example.teacherforboss.presentation.ui.survey.SurveyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

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
        val otherCheckboxs = arrayOf(
            binding.agreementCheckbox, binding.personalInformationCheckbox,
            binding.locationServiceCheckbox, binding.benefitInformationCheckbox,
            binding.smsCheckbox, binding.emailCheckbox, binding.ageCheckbox
        )

        //위의 두 리스트 합친것
        val combinedList=agreementList.zip(otherCheckboxs)

        //전체 동의
        binding.allCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                for(checkBox in otherCheckboxs) {
                    checkBox.isChecked = true
                }
                //viewModel에서 값들도 업데이트
//                for(agreement in agreementList){
//                    agreement.value="T"
//                }
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
            //체크된 agreemet 확인
            combinedList.forEach{(livedata,checkbox)->
                if(checkbox.isChecked==true){
                    livedata.value="T"
                }
            }
            viewModel.signupUser()

            //서버로 회원가입 api 보내기
//            viewModel.signup()



            //성공시 아래작업 진행
            val activity=activity as SignupActivity
            val intent = Intent(activity, BeginActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }
}