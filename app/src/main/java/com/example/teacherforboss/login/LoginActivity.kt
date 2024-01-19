package com.example.teacherforboss.login

import android.content.Intent
import android.content.ContentValues.TAG
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.teacherforboss.BeginActivity
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.teacherforboss.GlobalApplication
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityLoginBinding
import com.example.teacherforboss.login.kakao.KaKaoOauthViewModel
import com.example.teacherforboss.login.kakao.SocialLoginUiState
import com.example.teacherforboss.login.kakao.SocialLoginViewModel
import com.example.teacherforboss.signup.SignupActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

//@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel>()
    private val kakaoViewModel by viewModels<SocialLoginViewModel>()
    private val context=this
    private lateinit var kakaoOauthViewModel: KaKaoOauthViewModel
    @ApplicationContext val appContext=GlobalApplication.instance

//    @Inject
//    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //기본 로그인


        val token=TokenManager.getAccessToken(this)//ver1. shared preference
        //ver 1. shared prefs
        if(!token.isNullOrBlank()){
            // 로그인 실패 알림(ui 어케할지 질문->그냥 toast알람?)
        }
//        lifecycleScope.launch {
//            tokenManager.getAccessToken().collect(){ accessToken->
//                if(accessToken.isNullOrBlank()){
//                    showToast("로그인이 제대로 진행되지 않았습니다!")
//                }
//            }
//        }
        viewModel.loginResult.observe(this){
            when(it){
                is BaseResponse.Loading->{
                    // 기다려주세요 메시지?로고?
                }
                is BaseResponse.Success->{
                    processLogin(it.data)//respponse.result
                }
                is BaseResponse.Error->{
                    processError(it.msg)

                }
                else->{
                    //loading 종료시

                }
            }

        }

        //카카오 로그인 1

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                kakaoViewModel.socialLoginUiState.collect(){uiState->
                    when(uiState){
                        SocialLoginUiState.KakaoLogin->{
                            handleKakaoLogin()
                        }
                        SocialLoginUiState.LoginSuccess->{
                            showToast("kakao Login Success")
                        }
                        SocialLoginUiState.LoginFail->{
                            showToast("kakao Login Fail")
                        }
                        else->{

                        }

                    }
                }
            }
        }

        //kakao login 2
//        kakaoOauthViewModel=ViewModelProvider(this,KaKaoOauthViewModelFactory(application)).get(KaKaoOauthViewModel::class.java)
//
//        kakaoOauthViewModel.isLoggedIn.asLiveData().observe(this){ isLoggedIn->
//            val loginState=if (isLoggedIn) "logged in"  else "logout"
//            showToast(loginState)
//        }
        binding.loginBtn.setOnClickListener {
            doLogin()
        }

        binding.signup.setOnClickListener {

            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        }

        binding.kakaoBtn.setOnClickListener {
            kakaoViewModel.kakaoLogin()
            //kakaoOauthViewModel.kakaoLogin()
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
                TokenManager.saveAccessToken(appContext,it!!)
            }
//            lifecycleScope.launch {
//                tokenManager.saveAccessToken(data?.result?.accessToken!!)
//            }

        }
        if(!data?.result?.refreshToken.isNullOrEmpty()){
            data?.result?.refreshToken.let{
                TokenManager.saveRefreshToken(appContext,it!!)
            }
//            lifecycleScope.launch {
//                tokenManager.saveRefreshToken(data?.result?.refreshToken!!)
//            }
            //로그인 다음화면으로 navigation
        }

    }

    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    private fun handleKakaoLogin(){
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }


}
