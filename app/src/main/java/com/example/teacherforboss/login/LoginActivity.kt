package com.example.teacherforboss.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var id=binding.idBox.text
        var pw=binding.pwBox.text

        val token=TokenManager.getToken(this)
        if(!token.isNullOrBlank()){
            // 로그인 실패 알림(ui 어케할지 질문->그냥 toast알람?)
        }
        viewModel.loginResult.observe(this){
            when(it){
                is BaseResponse.Loading->{
                    // 기다려주세요 메시지?로고?
                }
                is BaseResponse.Success->{
                    processLogin(it.data)
                }
                is BaseResponse.Error->{
                    processError(it.msg)

                }
                else->{
                    //loading 종료시

                }
            }

        }

        binding.loginBtn.setOnClickListener {
            doLogin()
        }

    }
    fun doLogin(){
        val email=binding.idBox.text.toString()
        val password=binding.pwBox.text.toString()
        viewModel.loginUser(email,password)
    }
    fun processLogin(data:LoginResponse?){
        showToast("success:"+data?.message)
        if(!data?.result?.accessToken.isNullOrEmpty()){
            data?.result?.accessToken.let{
                TokenManager.saveToken(this,it!!)
            }
            //로그인 다음화면으로 navigation
        }

    }

    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}
