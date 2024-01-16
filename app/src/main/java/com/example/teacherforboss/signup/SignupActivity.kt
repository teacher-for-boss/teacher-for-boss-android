package com.example.teacherforboss.signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teacherforboss.BeginActivity
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    var tempTime = 0  //타이머 임시시간
    var code = "45689"  //임시 인증번호

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        checkPW()   //패스워드 같은지 확인
        checkCode() //인증번호 같은지 확인

        //인증하기버튼 누르면 타이머 시작
        binding.verify.setOnClickListener {
            binding.verify.visibility = View.INVISIBLE
            startTimer()
        }



        //회원가입 버튼 누르면 초기화면으로 이동
        binding.signup.setOnClickListener {

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
        val input_code = binding.codeBox.text.toString()

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