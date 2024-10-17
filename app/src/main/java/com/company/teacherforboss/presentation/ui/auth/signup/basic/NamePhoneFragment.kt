package com.company.teacherforboss.signup.fragment

import AppSignatureHelper
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.company.teacherforboss.R
import com.company.teacherforboss.data.model.response.BaseResponse
import com.company.teacherforboss.databinding.FragmentNamePhoneBinding
import com.company.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.util.base.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NamePhoneFragment : BindingFragment<FragmentNamePhoneBinding>(R.layout.fragment_name_phone) {
    private val viewModel by activityViewModels<SignupViewModel>()

    //사용자입력값
    var phone=""
    var phoneCode=""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        addListeners()
        binding.timeOverText.paintFlags = binding.timeOverText.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        viewModel.phone.observe(viewLifecycleOwner){
            viewModel.phone_validation()
            if(viewModel.phone.value.isNullOrEmpty()){
                binding.veryInfo.visibility = View.INVISIBLE
            }
            else{
                binding.veryInfo.visibility = View.VISIBLE
            }

        }

        //휴대폰 인증결과 수신
        viewModel.phoneResult.observe(viewLifecycleOwner) {
            when(it) {
                is BaseResponse.Loading->{}
                is BaseResponse.Success->{
                    binding.veryInfo.text = getString(R.string.verify_phone_info)
                    binding.phoneVerifyBtn.isEnabled = false
                    binding.inputPhoneCode.visibility=View.VISIBLE
                    binding.timeOverText.visibility=View.VISIBLE
                    viewModel.startTimer()

                    Log.d("auth",it.data?.result?.phoneAuthId.toString())
                    viewModel.phoneAuthId.value=it.data?.result?.phoneAuthId!!
                }
                is BaseResponse.Error->{
                    if(it.msg == "이미 가입된 전화번호입니다.") {
                        binding.veryInfo.text = getString(R.string.phone_unavailable)

                        binding.phoneVerifyBtn.isEnabled = false
                        binding.inputPhoneCode.visibility=View.INVISIBLE
                        viewModel.stopTimer()
                        binding.timeOverText.visibility=View.INVISIBLE
                    }
                    else  {
                        showToast("error:"+it.msg)
                    }

                }

                else -> {}
            }
        }

        viewModel.phoneCheckResult.observe(viewLifecycleOwner) {
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
                    if(it.data?.isSuccess!!&&it.data?.result?.checked!!){
                        viewModel._isPhoneVerified_str.value="T"
                        viewModel._isPhoneVerified.value=true
                        viewModel.stopTimer()
                        binding.phoneConfirmBtn.isEnabled = false
                    }
                    binding.checkVery.visibility=View.VISIBLE
                    binding.timeOverText.visibility = View.INVISIBLE
                }
                is BaseResponse.Error->{
                    viewModel._isPhoneVerified_str.value="F"
                    viewModel._isPhoneVerified.value=false
                    binding.checkVery.visibility=View.VISIBLE
                }

                else -> {}
            }
        }
    }

    private fun addListeners() {
        val activity=activity as SignupActivity
        val helper=AppSignatureHelper(activity)
        val hash=helper.getAppSignatures()?.get(0)

        with(binding) {
            nextBtn.setOnClickListener {
                activity.gotoNextFragment(GenderBirthFragment())
            }

            // 휴대폰 인증하기버튼 눌렀을때
            phoneVerifyBtn.setOnClickListener {
                veryInfo.visibility=View.VISIBLE

                if(viewModel.phone_check.value==true){
                    viewModel.phoneUser(hash.toString())
                }
            }

            // 휴대폰 코드 입력 후 확인 버튼
            phoneConfirmBtn.setOnClickListener {
                phoneCode = phoneCodeBox.text.toString()
                viewModel.phoneCheckUser(phoneCode)
            }

            // 인증번호 다시 받기
            timeOverText.setOnClickListener{
                if (viewModel.timeOverState.value == true){
                    viewModel.phoneUser(hash.toString())
                    phoneCodeBox.text.clear()
                    checkVery.visibility = View.INVISIBLE
                }
                else {
                    if(timeOverText.text == getString(R.string.timeover_false)) {
                        timeOverText.text = getString(R.string.timeover_true)
                    } else {
                        viewModel.phoneUser(hash.toString())
                        phoneCodeBox.text.clear()
                        checkVery.visibility = View.INVISIBLE
                    }
                }
            }

        }
    }

    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }

}