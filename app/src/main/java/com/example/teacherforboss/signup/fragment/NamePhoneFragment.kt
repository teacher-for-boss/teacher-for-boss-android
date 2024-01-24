package com.example.teacherforboss.signup.fragment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentEmailBinding
import com.example.teacherforboss.databinding.FragmentNamePhoneBinding
import com.example.teacherforboss.signup.SignupActivity
import com.example.teacherforboss.signup.SignupViewModel

class NamePhoneFragment : Fragment() {
    private lateinit var binding: FragmentNamePhoneBinding
    private val viewModel: SignupViewModel by viewModels()

    var tempTime = 0  //타이머 임시시간

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

        //이메일 인증하기버튼 눌렀을때
        binding.phoneVerifyBtn.setOnClickListener {
            binding.phoneVerifyBtn.visibility = View.INVISIBLE
            binding.veryInfo.visibility=View.VISIBLE
            startTimer()  //타이머 시작

        }
        return binding.root
    }

    //verify 버튼을 누르면 3분 타이머
    fun startTimer() {
        lateinit var timer: CountDownTimer

        binding.timer.visibility = View.VISIBLE

        timer = object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tempTime = millisUntilFinished.toInt()
                updateTime()
            }

            override fun onFinish() {}

        }.start()

    }

    fun updateTime() {
        val min = tempTime % 3600000 / 60000
        val sec = tempTime % 3600000 % 60000 / 1000

        var timeLeft = "$min : "

        if (sec < 10) timeLeft += "0"

        timeLeft += sec

        binding.timer.text = timeLeft
    }

    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }


}