package com.example.teacherforboss.signup.fragment

import AppSignatureHelper
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentNamePhoneBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.login.BaseResponse
import com.example.teacherforboss.signup.SignupActivity
import com.example.teacherforboss.signup.SignupViewModel

class NamePhoneFragment : Fragment() {
    private lateinit var binding: FragmentNamePhoneBinding
    private val viewModel: SignupViewModel by viewModels()

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
        binding.nextBtn.setOnClickListener {
            activity.gotoNextFragment(GenderBirthFragment())
        }

        //폰 인증 api email과 동일하게 구현하기
        //viewModel에 phone, isPhoneVerified 추가

        //휴대폰 인증하기버튼 눌렀을때
        binding.phoneVerifyBtn.setOnClickListener {
            binding.phoneVerifyBtn.visibility = View.INVISIBLE
            binding.veryInfo.visibility=View.VISIBLE
            binding.inputPhoneCode.visibility=View.VISIBLE
            startTimer()

            phone = binding.phoneNumBox.text.toString()
            viewModel.phoneUser(viewModel.phone.value.toString())

        }

        //휴대폰 인증결과 수신
        viewModel.phoneResult.observe(viewLifecycleOwner) {
            when(it) {
                is BaseResponse.Loading->{}
                is BaseResponse.Success->{
                    viewModel.phoneAuthId=it.data?.result?.phoneAuthId!!
                }
                is BaseResponse.Error->{
                    showToast("error"+it.msg)
                }
            }
        }

        //휴대폰 코드 입력 후 확인 버튼
        binding.phoneConfirmBtn.setOnClickListener {
            phoneCode = binding.phoneCodeBox.text.toString()
            viewModel.phoneCheckUser(viewModel.phoneAuthId, phoneCode)
        }

        viewModel.phoneCheckResult.observe(viewLifecycleOwner) {
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
                    viewModel.setPhoneVerifiedStatus(it.data?.isSuccess!!&&it.data?.result?.checked!!)
                    binding.checkVery.visibility=View.VISIBLE

                    var tempPhoneMap = mutableMapOf<String, LiveData<Boolean>>()
                    tempPhoneMap[phone]=viewModel.isPhoneVerified
                    viewModel.confirmedPhone.postValue(tempPhoneMap)
                }
                is BaseResponse.Error->{
                    showToast("error:"+it.msg)
                }
            }
        }


        return binding.root
    }

    //verify 버튼을 누르면 3분 타이머
    fun startTimer() {
        lateinit var timer: CountDownTimer

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