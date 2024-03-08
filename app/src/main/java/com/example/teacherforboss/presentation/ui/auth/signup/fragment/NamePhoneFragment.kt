package com.example.teacherforboss.signup.fragment

import AppSignatureHelper
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.intl.Locale
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentNamePhoneBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

//@AndroidEntryPoint
class NamePhoneFragment : Fragment() {
    private lateinit var binding: FragmentNamePhoneBinding
    private val viewModel by activityViewModels<SignupViewModel>()

    //사용자입력값
    var phone=""
    var phoneCode=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_name_phone,container,false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this



        val activity=activity as SignupActivity

        val helper=AppSignatureHelper(activity)
        val hash=helper.getAppSignatures()?.get(0)
        Log.d("auth hash",hash.toString())

        binding.nextBtn.setOnClickListener {
            activity.gotoNextFragment(GenderBirthFragment())
        }

        //폰 인증 api email과 동일하게 구현하기
        //viewModel에 phone, isPhoneVerified 추가

        //휴대폰 반응형(-) 추가
        viewModel.livePhone1.observe(viewLifecycleOwner, Observer {
            if (it.length==3){
                binding.phoneNumBox2.requestFocus()
            }
        })
        viewModel.livePhone2.observe(viewLifecycleOwner, Observer {
            if (it.length==4){
                binding.phoneNumBox3.requestFocus()
            }
        })

        //휴대폰 인증하기버튼 눌렀을때
        binding.phoneVerifyBtn.setOnClickListener {
            viewModel.phone_validation()

            if(viewModel.phone_check.value==true){
                binding.phoneCodeBox.visibility=View.VISIBLE
                startTimer()
                viewModel.phoneUser(hash.toString())
                binding.veryInfo.visibility=View.VISIBLE
            }

        }

        //휴대폰 인증결과 수신
        viewModel.phoneResult.observe(viewLifecycleOwner) {
            when(it) {
                is BaseResponse.Loading->{}
                is BaseResponse.Success->{
                    binding.veryInfo.text="인증번호가 발송되었습니다."
                    binding.phoneVerifyBtn.visibility = View.INVISIBLE
                    binding.inputPhoneCode.visibility=View.VISIBLE
                    startTimer()

                    Log.d("auth",it.data?.result?.phoneAuthId.toString())
                    viewModel.phoneAuthId.value=it.data?.result?.phoneAuthId!!
                }
                is BaseResponse.Error->{
                    if(it.msg=="이미 가입된 전화번호입니다."){
                        binding.veryInfo.text="이미 가입된 전화번호입니다."
                    }
                    else{
                        showToast("error"+it.msg)
                    }
                }

                else -> {}
            }
        }

        //휴대폰 코드 입력 후 확인 버튼
        binding.phoneConfirmBtn.setOnClickListener {
            phoneCode = binding.phoneCodeBox.text.toString()
            viewModel.phoneCheckUser(phoneCode)
        }

        viewModel.phoneCheckResult.observe(viewLifecycleOwner) {
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
//                    viewModel.setPhoneVerifiedStatus(it.data?.isSuccess!!&&it.data?.result?.checked!!)
                    if(it.data?.isSuccess!!&&it.data?.result?.checked!!){
                        viewModel._isPhoneVerified_str.value="T"
                        viewModel._isPhoneVerified.value=true
                    }
                    binding.checkVery.visibility=View.VISIBLE

//                    var tempPhoneMap = mutableMapOf<String, LiveData<Boolean>>()
//                    tempPhoneMap[phone]=viewModel.isPhoneVerified
                    //viewModel.confirmedPhone.postValue(tempPhoneMap)
                }
                is BaseResponse.Error->{
                    showToast("error:"+it.msg)
                }

                else -> {}
            }
        }


        return binding.root
    }

    //verify 버튼을 누르면 3분 타이머
    fun startTimer() {
        binding.timer.visibility = View.VISIBLE

        val timer = object : CountDownTimer(181000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTime(millisUntilFinished)
            }

            override fun onFinish() { }
        }

        timer.start()
    }

    fun updateTime(millisUntilFinished: Long) {
        val min = millisUntilFinished / 60000
        val sec = (millisUntilFinished % 60000) / 1000
        val formattedMin = String.format("%02d", min)
        val formattedSec = String.format("%02d", sec)

        val timeLeft = "$formattedMin:$formattedSec"

        binding.timer.text = timeLeft
    }

    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }


}