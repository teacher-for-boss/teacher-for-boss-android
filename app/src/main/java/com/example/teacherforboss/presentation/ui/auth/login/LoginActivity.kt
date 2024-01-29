package com.example.teacherforboss.presentation.ui.auth.login

import android.content.Intent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.teacherforboss.presentation.ui.main.BeginActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.teacherforboss.GlobalApplication
import com.example.teacherforboss.databinding.ActivityLoginBinding
import com.example.teacherforboss.presentation.ui.auth.common.BaseResponse
import com.example.teacherforboss.presentation.ui.auth.login.social.SocialLoginUiState
import com.example.teacherforboss.presentation.ui.auth.login.social.SocialLoginViewModel
import com.example.teacherforboss.presentation.ui.auth.login.social.socialLoginResponse
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
//import dagger.hilt.android.qualifiers.ActivityContext
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

//@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    val KAKAOLOGIN="KAKAOLOGIN"
    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel>()
    private val kakaoViewModel by viewModels<SocialLoginViewModel>()
    private val context=this

    val appContext=GlobalApplication.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //기본 로그인
        val token= TokenManager.getAccessToken(this)//ver1. shared preference
        //ver 1. shared prefs
        if(!token.isNullOrBlank()){
            // 로그인 실패 알림(ui 어케할지 질문->그냥 toast알람?)
        }

        viewModel.loginResult.observe(this){
            when(it){
                is BaseResponse.Loading ->{
                    // 기다려주세요 메시지?로고?
                }
                is BaseResponse.Success ->{
                    showToast("로그인 성공")
                    saveToken(it.data)//respponse.result

                }
                is BaseResponse.Error ->{
                    processError(it.msg)

                }
                else->{
                    //loading 종료시

                }
            }

        }
        viewModel.socialLoginResult.observe(this){
            when(it){is BaseResponse.Loading ->{
                showToast("카카오로 로그인 중입니다.조금만 더 기다려주세요 🐣")
                // 기다려주세요 메시지?로고?
            }
                is BaseResponse.Success ->{
                    showToast("카카오로 로그인 완료!🐣")
                    saveToken(it.data)//respponse.result
                    startActivity(intent)

                }
                is BaseResponse.Error ->{
                    processError(it.msg)
                    Log.e("kakao",it.msg.toString())

                }
                else->{
                    //loading 종료시

                }
            }
        }

        //카카오 로그인 1
        val intent=Intent(this,BeginActivity::class.java)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                kakaoViewModel.socialLoginUiState.collect(){uiState->
                    when(uiState){
                        SocialLoginUiState.KakaoLogin->{
                            handleKakaoLogin()
                        }
                        SocialLoginUiState.LoginSuccess->{
                            showToast("kakao Login Success")
                            //getAgreement()
                            getToken() //개발 확인용 나중엔 삭제
                            getUserInfo()

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

        binding.loginBtn.setOnClickListener {
            doLogin()
        }

        binding.signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        }

        binding.kakaoBtn.setOnClickListener {
            kakaoViewModel.kakaoLogin()
        }


    }
    fun doLogin(){
        val email=binding.idBox.text.toString()
        val password=binding.pwBox.text.toString()
        viewModel.loginUser(email,password)
    }
    fun <T:loginInterface>saveToken(data: T?){
        showToast("login success:"+data?.message)
        if(!data?.result?.accessToken.isNullOrEmpty()){
            data?.result?.accessToken.let{
                TokenManager.saveAccessToken(appContext, it!!)
            }

        }
        if(!data?.result?.refreshToken.isNullOrEmpty()){
            data?.result?.refreshToken.let{
                TokenManager.saveRefreshToken(appContext, it!!)
            }
        }

    }


    // kakao
    private fun handleKakaoLogin(){
        //Log.d("kakao",checkTokenState().toString())
//        Log.d("kakao","handleKakaoLogin():")
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                kakaoViewModel.kakaoLoginSuccess()
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
                    kakaoViewModel.kakaoLoginSuccess()

                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun handleKakaoLogout(){
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)

            }
            else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")

            }
        }

    }
    fun checkTokenState(){
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                        //로그인 필요
                        handleKakaoLogin()
                    }
                    else {
                        //기타 에러
                    }
                }
                else {
                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                    //로그인된 상태로 바로가기?
                }
            }
        }
        else {
            handleKakaoLogin()
            //로그인 필요
        }
    }

    //우선 나중에 추가
    private fun getAgreement(){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                var scopes = mutableListOf<String>()

                if (user.kakaoAccount?.emailNeedsAgreement == true) { scopes.add("account_email") }
                if (user.kakaoAccount?.birthdayNeedsAgreement == true) { scopes.add("birthday") }
                if (user.kakaoAccount?.birthyearNeedsAgreement == true) { scopes.add("birthyear") }
                if (user.kakaoAccount?.genderNeedsAgreement == true) { scopes.add("gender") }
                if (user.kakaoAccount?.phoneNumberNeedsAgreement == true) { scopes.add("phone_number") }
                if (user.kakaoAccount?.profileNeedsAgreement == true) { scopes.add("profile") }

                if (scopes.count() > 0) {
                    Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다.")

                    // OpenID Connect 사용 시
                    // scope 목록에 "openid" 문자열을 추가하고 요청해야 함
                    // 해당 문자열을 포함하지 않은 경우, ID 토큰이 재발급되지 않음
                    // scopes.add("openid")

                    //scope 목록을 전달하여 카카오 로그인 요청
                    UserApiClient.instance.loginWithNewScopes(context, scopes) { token, error ->
                        if (error != null) {
                            Log.e(TAG, "사용자 추가 동의 실패", error)
                        } else {
                            Log.d(TAG, "allowed scopes: ${token!!.scopes}")

                            // 사용자 정보 재요청
                            UserApiClient.instance.me { user, error ->
                                if (error != null) {
                                    Log.e(TAG, "사용자 정보 요청 실패", error)
                                }
                                else if (user != null) {
                                    Log.i(TAG, "사용자 정보 요청 성공")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getToken(){
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e(TAG, "토큰 정보 보기 실패", error)
            }
            else if (tokenInfo != null) {
                Log.i(TAG, "토큰 정보 보기 성공" +
                        "\n회원번호: ${tokenInfo.id}" +
                        "\n만료시간: ${tokenInfo.expiresIn} 초")
            }
        }
    }

    private fun getUserInfo(){
        val prefs: SharedPreferences =
            context.getSharedPreferences(KAKAOLOGIN, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        var email:String=""
        var phoneNumber:String=""
        var name:String=""
        var gender:Int?=0
        var birthDate: LocalDate?
        var birthDate_str:String?=""
        var imageUrl:String?=""


        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(TAG, "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n이름: ${user.kakaoAccount?.name}" +
                        "\n폰번호: ${user.kakaoAccount?.phoneNumber}" +

                        "\n성별: ${user.kakaoAccount?.gender}" +
                        "\n생일: ${user.kakaoAccount?.birthday}" +
                        "\n년도: ${user.kakaoAccount?.birthyear}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                )

                //ver 1. sharedpreference에 저장후 다른 함수(getUserInfo다음에 바로 호출)에서 api요청시 sharedpreference에서 값들을 가져와서 사용
                editor.putString("email",user.kakaoAccount?.email)
                editor.putString("name",user.kakaoAccount?.name)
                editor.putString("phoneNumber",user.kakaoAccount?.phoneNumber)
                editor.putString("gender",user.kakaoAccount?.gender.toString())
                editor.putString("birthDate",user.kakaoAccount?.birthyear+user.kakaoAccount?.birthday)
                editor.putString("imageUrl",user.kakaoAccount?.profile?.thumbnailImageUrl)

                editor.apply()

                //ver 2. 그냥 여기서 바로 api 요청한다.
                email=user.kakaoAccount?.email!!
                name=user.kakaoAccount?.name!!
                phoneNumber=user.kakaoAccount?.phoneNumber!!

                if(user.kakaoAccount?.gender.toString()=="남자"){
                    gender=1
                }
                else{
                    gender=2
                }
                birthDate_str=user.kakaoAccount?.birthyear.toString()+user.kakaoAccount?.birthday.toString()
                val formatter=DateTimeFormatter.ofPattern("yyyyMMdd")
                val birthDate=LocalDate.parse(birthDate_str,formatter)

                imageUrl=user.kakaoAccount?.profile?.thumbnailImageUrl

                viewModel.socialLogin(email,name,phoneNumber,gender, birthDate = birthDate,imageUrl)
            }
        }
    }

    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }




}
