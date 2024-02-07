package com.example.teacherforboss.signup.fragment

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentEmailBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.fragment.PasswordFragment


class EmailFragment : Fragment() {
    private lateinit var binding:FragmentEmailBinding
    private val viewModel: SignupViewModel by viewModels()

    //사용자입력값
    var email=""
    var emailCode=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_email,container,false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        val activity=activity as SignupActivity

        binding.nextBtn.setOnClickListener {
            activity.gotoNextFragment(PasswordFragment())
        }

        //이메일 인증하기버튼 눌렀을때
        binding.emailVerifyBtn.setOnClickListener {
            binding.emailVerifyBtn.visibility = View.INVISIBLE
            binding.veryInfo.visibility=View.VISIBLE
            binding.inputEmailCode.visibility=View.VISIBLE
            startTimer()  //타이머 시작

            email=binding.emailBox.text.toString()
            viewModel.emailUser(viewModel.email.value.toString()) //서버로 auth/email

        }
        //이메일 인증결과 수신
        viewModel.emailResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
                    viewModel.emailAuthId=it.data?.result?.emailAuthId!!//result로 전달받은 emailAuthId 저장

                }
                is BaseResponse.Error->{
                    showToast("error:"+it.msg)
                }
            }
        }

        //이메일 코드 입력 후 확인 버튼
        binding.emailConfirmBtn.setOnClickListener {
            emailCode=binding.emailCodeBox.text.toString()
            viewModel.emailCheckUser(viewModel.emailAuthId,emailCode) //서버로 /auth/email/check
        }

        viewModel.emailCheckResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
                    viewModel.setEmailVerifiedStatus(it.data?.isSuccess!!&&it.data?.result?.checked!!)
                    binding.checkVery.visibility=View.VISIBLE

                    //후에 회원가입 버튼 클릭시 현재입력되어있는(liveEmail,viewModel email)값이 confirmed map에 있는지 확인 후
                    //없다면 이메일 인증을 다시 받아야 하니 그거에 맞는 안내 알림 설정!
                    //ex) 사용자가 a라는 이메일로 인증받고 b라는 이메일을 다시 입력했을 경우 방지를 위함
                    var tempEmailMap= mutableMapOf<String, LiveData<Boolean>>()
                    tempEmailMap[email]=viewModel.isEmailVerified
                    viewModel.confirmedEmail.postValue(tempEmailMap)

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