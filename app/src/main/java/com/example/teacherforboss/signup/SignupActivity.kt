package com.example.teacherforboss.signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.teacherforboss.BeginActivity
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivitySignupBinding
import org.apache.commons.lang3.mutable.Mutable
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel:SignupViewModel by viewModels()

    var tempTime = 0  //타이머 임시시간
    var code = "45689"  //임시 인증번호

    //pw check 정규식
    val num_regex:Regex=Regex("[0-9]+")
    val eng_regex:Regex=Regex("[a-zA-z]+")
    val special_regex:Regex= Regex("[^a-zA-Z0-9가-힣]+")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding= ActivitySignupBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        var email=""
        var emailCode=""
        var isEmailVerified=false
        var confirmedEmailMap= mutableMapOf<String,Boolean>()

        binding=DataBindingUtil.setContentView(this,R.layout.activity_signup)
        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        viewModel.livePw.observe(this, Observer {
            Log.d("live","live pw:${it}")

            viewModel.eng_check.value=(eng_regex.find(it)!=null)
            Log.d("regex","eng regx:${viewModel.special_check.value}")

            viewModel.num_check.value=(num_regex.find(it)!=null)
            Log.d("regex","num check:${viewModel.num_check.value}")

            viewModel.special_check.value=(special_regex.find(it)!=null)
            Log.d("regex","special regx:${viewModel.special_check.value}")

            viewModel.length_check.value=(it.toString().length>8 && it.toString().length<20)
            Log.d("regex","length regx:${viewModel.length_check.value}")

            viewModel.all_check.value=(viewModel.eng_check.value!!&& viewModel.num_check.value!! && viewModel.special_check.value!!&& viewModel.length_check.value!!)
            Log.d("rePw","all check:${viewModel.all_check.value}")

        })

        viewModel.liveRePw.observe(this,Observer{
            if(viewModel.all_check.value==true)binding.pwInfo.visibility = View.VISIBLE
            viewModel.rePw_check.value=(viewModel.livePw.value.equals(it.toString()))
            Log.d("rePw","repw_check:${viewModel.rePw_check.value}")
        })



        checkPW()   //패스워드 같은지 확인
        checkCode() //인증번호 같은지 확인

        //인증하기버튼 누르면 타이머 시작
        binding.emailVerifyBtn.setOnClickListener {
            binding.emailVerifyBtn.visibility = View.INVISIBLE
            binding.veryInfo.visibility=View.VISIBLE
            startTimer()
            email=binding.idBox.text.toString()
        }

        //이메일 코드 입력 후 확인 버튼
        binding.emailVerifyBtn.setOnClickListener {
            emailCode=binding.emailCodeBox.text.toString()
            //서버와 통신 /auth/email/check

            // isSuccess
            isEmailVerified=true
            confirmedEmailMap[email]=isEmailVerified
            //후에 회원가입 버튼 클릭시 현재입력되어있는(liveEmail)값이 저 map에 있는지 확인 후
            //없다면 이메일 인증을 다시 받아야 하니 그거에 맞는 안내 알림 설정!
            //ex) 사용자가 a라는 이메일로 인증받고 b라는 이메일을 다시 입력했을 경우 방지를 위함

//            viewModel.setEmailVerifiedStatus(isVefiried = true)
//            var tempEmailMap= mutableMapOf<String,LiveData<Boolean>>()
//            tempEmailMap[email]=viewModel.isEmailVerified
//            viewModel.confirmedEmail.postValue(tempEmailMap)
        }

        //핸드폰 인증하기 버튼 누르기
        binding.phoneVerifyBtn.setOnClickListener {
            binding.phoneVeryInfo.visibility=View.VISIBLE
        }



        //회원가입 버튼 누르면 초기화면으로 이동
        binding.signupBtn.setOnClickListener {
            //서버와 통신

            confirmedEmailMap.containsKey(viewModel.liveEmail.value.toString())

            if (checkPW() && checkCode()) {
                val intent = Intent(this, BeginActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "비밀번호 또는 인증번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //pwBox 와 pwReEnterBox 가 같은지 비교
    fun checkPW(): Boolean {
        val pw1 = binding.pwBox.text.toString()
        val pw2 = binding.pwReEnterBox.text.toString()

        //사용자가 비밀번호 재입력을 한다면
        if (pw2.isNotEmpty()) {

            binding.pwInfo.visibility = View.VISIBLE

            if (pw1 == pw2) {
                binding.pwInfo.text = "비밀번호가 일치합니다"
                binding.pwInfo.setTextColor(Color.GREEN)
                return true
            }
            else {
                binding.pwInfo.text = "비밀번호가 일치하지 않습니다"
                binding.pwInfo.setTextColor(Color.RED)
                return false
            }
        }

        //사용자가 비밀번호를 재입력하지 않은 경우
        return false
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

    //인증번호가 같은지 확인
    fun checkCode(): Boolean {
        val input_code = binding.emailCodeBox.text.toString()

        //사용자가 인증번호를 입력한다면
        if (input_code.isNotEmpty()) {

            binding.checkVery.visibility = View.VISIBLE


            if(code == input_code) {
                binding.checkVery.text = "인증되었습니다."
                binding.checkVery.setTextColor(Color.GREEN)
                return true
            }
            else {
                binding.checkVery.text = "인증번호가 틀립니다."
                binding.checkVery.setTextColor(Color.RED)
                return false
            }
        }

        return false
    }

}