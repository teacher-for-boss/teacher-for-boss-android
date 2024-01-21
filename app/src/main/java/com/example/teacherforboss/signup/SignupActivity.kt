package com.example.teacherforboss.signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.teacherforboss.BeginActivity
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivitySignupBinding
import com.example.teacherforboss.login.BaseResponse
import com.example.teacherforboss.login.LoginActivity
import com.example.teacherforboss.login.LoginResponse
import com.example.teacherforboss.login.TokenManager
import com.example.teacherforboss.signup.api.SignupResponse
import com.example.teacherforboss.signup.fragment.AgreementFragment
import com.example.teacherforboss.signup.fragment.EmailFragment
import com.example.teacherforboss.signup.fragment.GenderBirthFragment
import com.example.teacherforboss.signup.fragment.NamePhoneFragment
import com.example.teacherforboss.signup.fragment.PasswordFragment
import org.apache.commons.lang3.mutable.Mutable
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel:SignupViewModel by viewModels()
    var index=0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding=DataBindingUtil.setContentView(this,R.layout.activity_signup)
//        binding.signupViewModel=viewModel

        val fragmentManager:FragmentManager=supportFragmentManager

        fragmentManager
            .beginTransaction()
            .add(R.id.fragment_container,EmailFragment())
            .commit()

        binding.backBtn.setOnClickListener{
            if(fragmentManager.backStackEntryCount>0){
                fragmentManager.popBackStack()
            }
            else{
                //메인 홈화면으로 이동
            }

        }

        binding.nextBtn.setOnClickListener {
            val transaction=fragmentManager.beginTransaction()
            Log.d("index","${index}")
            when(index){
                0->{
                    index=1
                    transaction.replace(R.id.fragment_container,PasswordFragment())

                }
                1->{
                    index=2
                    transaction.replace(R.id.fragment_container,NamePhoneFragment())

                }
                2->{
                    index=3
                    transaction.replace(R.id.fragment_container,GenderBirthFragment())

                }
                3->{
                    index=4
                    transaction.replace(R.id.fragment_container,AgreementFragment())

                }
                4->{
                    // 서버로 회원가입 api 요청
                    viewModel.signupUser()
                    viewModel.signupResult.observe(this,{
                        when(it){
                            is BaseResponse.Loading->{
                                // 기다려주세요 메시지?로고?
                            }
                            is BaseResponse.Success->{
                                processSignup(it.data)//respponse.result
                            }
                            is BaseResponse.Error->{
                                processError(it.msg)
                            }
                            else->{
                                //loading 종료시

                            }
                        }

                    })

                }
            }
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    fun processSignup(data: SignupResponse?){
        showToast("${data?.message}")
        if(data?.isSuccess==true){
            //로그인 액티비티로 이동
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}