package com.example.teacherforboss.presentation.ui.auth.login

import android.content.Intent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.teacherforboss.GlobalApplication
import com.example.teacherforboss.databinding.ActivityLoginBinding
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.data.model.response.login.LoginResponseInterface
import com.example.teacherforboss.data.tokenmanager.TokenManager
import com.example.teacherforboss.presentation.ui.auth.findinfo.screens.FindPwActivity
import com.example.teacherforboss.presentation.ui.auth.login.social.SocialLoginUiState
import com.example.teacherforboss.presentation.ui.auth.login.social.SocialLoginViewModel

import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.util.base.LocalDataSource
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding

    private val signupViewModel by viewModels<SignupViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()
    private val socialLoginViewModel by viewModels<SocialLoginViewModel>()
    private val context=this

    val appContext=GlobalApplication.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("test","T")
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //기본 로그인
        val token= TokenManager.getAccessToken(this)//ver1. shared preference
        if(!token.isNullOrBlank()){

        }

        //기본 로그인
        loginViewModel.loginResult.observe(this){
            when(it){
                is BaseResponse.Loading ->{
                    // 로딩창
                }
                is BaseResponse.Success ->{
                    saveToken(it.data)//respponse.result
                    LocalDataSource.saveUserName(appContext,it.data?.result?.name?:"".toString())
//                    saveUserName(appContext,it.data?.result?.name?:"".toString())//survery start 사장님 이름
                }
                is BaseResponse.Error ->{
                    processError(it.msg)

                }
                else->{
                    //loading 종료시
                }
            }
        }

        // 소셜 로그인
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                socialLoginViewModel.socialLoginUiState.collect(){uiState->
                    when(uiState){
                        SocialLoginUiState.KakaoLogin->{
                            handleKakaoLogin()
                        }
                        SocialLoginUiState.NaverLogin->{
                            handleNaverLogin()
                        }
                        SocialLoginUiState.KakaoLoginSuccess->{
                            getKakaoUserInfo()
                        }
                        SocialLoginUiState.LoginFail->{
                            showToast("social Login Fail")
                        }
                        else->{
                        }

                    }
                }
            }
        }

        // 소셜 로그인 (3.로그인 요청)
        loginViewModel.socialLoginResult.observe(this){
            when(it){
                is BaseResponse.Loading ->{}
                is BaseResponse.Success ->{
                    saveToken(it.data)//respponse.result
                    LocalDataSource.saveUserName(appContext,it.data?.result?.name!!.toString())
                }
                is BaseResponse.Error ->{
//                    processError(it.msg)
                    // 회원가입 진행
                    loginViewModel._isSocialLoginSignup.value=true
                    gotoSignupActivity()

                }
                else->{
                    //loading 종료시
                }
            }
        }

        binding.loginBtn.setOnClickListener {
            val email=binding.idBox.text.toString()
            val password=binding.pwBox.text.toString()
            loginViewModel.loginUser(email,password)
        }

        binding.signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.kakaoBtn.setOnClickListener {
            socialLoginViewModel.kakaoLogin()
        }
        binding.naverBtn.setOnClickListener {
            socialLoginViewModel.naverLogin()
        }


        binding.findEmailBtn.setOnClickListener {
            val intent=Intent(this, FindPwActivity::class.java)
            intent.putExtra("destination","email")
            startActivity(intent)
        }

        binding.findPwBtn.setOnClickListener {
            val intent=Intent(this,FindPwActivity::class.java)
            intent.putExtra("destination","pw")
            startActivity(intent)
        }


    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }


    //access, refresh token 저장
    fun <T: LoginResponseInterface>saveToken(data: T?){
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
    //user name 저장
    fun saveUserName(context: Context, name:String){
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(USER_NAME, name)
        editor.apply()

    }
    //naver
    private fun handleNaverLogin(){
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 API 호출 성공 시 유저 정보를 가져옴
                NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                    override fun onSuccess(result: NidProfileResponse) {
                        with(signupViewModel){
                            _name.value=result.profile?.name.toString()
                            liveEmail.value=result.profile?.email.toString()
                            livePhone.value=result.profile?.mobile.toString().replace("-","")
                            _birthDate.value=result.profile?.birthday.toString()
                            _profileImg.value=result.profile?.profileImage.toString()
                        }

                        var gender = result.profile?.gender.toString()
                        var birthYear=result.profile?.birthYear.toString()
                        var birthDay=result.profile?.birthday.toString()
                        var name = result.profile?.name.toString()
                        var email = result.profile?.email.toString()
                        var phoneNumber=result.profile?.mobile.toString()
                        var imageUrl=result.profile?.profileImage.toString()

                        var gender_int=1
                        //사용자 정보 전처리
                        if(gender=="M"){
                            gender_int=1
                        }
                        else gender_int=2

                        signupViewModel._birthDate.value=birthYear+"-"+birthDay

                        LocalDataSource.saveUserInfo(context ,"name",result.profile?.name.toString())
                        LocalDataSource.saveUserInfo(context,"email",result.profile?.email.toString())
                        LocalDataSource.saveUserInfo(context,"phone",result.profile?.mobile.toString().replace("-",""))
                        LocalDataSource.saveUserInfo(context,"birthDate",birthYear+"-"+birthDay)
                        LocalDataSource.saveUserInfo(context,"profileImg",result.profile?.profileImage.toString())
                        LocalDataSource.saveUserInfo(context,"gender",gender_int.toString())

                        Log.e("naver", "네이버 로그인한 유저 정보 - 이름 : $name")
                        Log.e("naver", "네이버 로그인한 유저 정보 - 이메일 : $email")
                        Log.e("naver", "네이버 로그인한 유저 정보 - 성별 : $gender")
                        Log.e("naver", "네이버 로그인한 유저 정보 - 폰번호 : $phoneNumber")
                        Log.e("naver", "네이버 로그인한 유저 정보 - 생년 월일 : $birthYear+${birthDay}")
                        Log.e("naver", "네이버 로그인한 유저 정보 - 이미지 : $imageUrl")

                        // 소셜 로그인 요청
                        loginViewModel.socialLogin(type="naver",email)
                        LocalDataSource.saveSignupType(context,SIGNUP_SOCIAL_NAVER)
                        signupViewModel._socialType.value=3
                    }


                    override fun onError(errorCode: Int, message: String) {
                        //
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        //
                    }
                })
            }


            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.e("naver", "$errorCode $errorDescription")
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)

    }

    // kakao
    private fun handleKakaoLogin(){
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                socialLoginViewModel.kakaoLoginSuccess()
                //getKakaoUserInfo()
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
                    //getKakaoUserInfo()

                    socialLoginViewModel.kakaoLoginSuccess()

                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun getKakaoUserInfo(){

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


                // 티쳐 포 보스 소셜 로그인 api 요청

                //데이터 전처리
                with(signupViewModel){
                    liveEmail.value=user.kakaoAccount?.email!!
                    _name.value=user.kakaoAccount?.name!!
                    livePhone.value=user.kakaoAccount?.phoneNumber!!
                        .replace("+82","0")
                        .replace("-","")
                        .replace(" ","")
                    _profileImg.value=user.kakaoAccount?.profile?.thumbnailImageUrl
                }

                if(user.kakaoAccount?.gender.toString()=="남자"){
                    gender=1
                }
                else{
                    gender=2
                }
                signupViewModel._gender.value=gender

                birthDate_str=user.kakaoAccount?.birthyear.toString()+user.kakaoAccount?.birthday.toString()
                val formatter=DateTimeFormatter.ofPattern("yyyyMMdd")
                val birthDate=LocalDate.parse(birthDate_str,formatter)
                val formatted_phone=user.kakaoAccount?.phoneNumber!!
                    .replace("+82","0")
                    .replace("-","")
                    .replace(" ","")
                signupViewModel._birthDate.value=birthDate.toString()
                signupViewModel._socialType.value=2

                LocalDataSource.saveUserInfo(context,"name",user.kakaoAccount?.name!!)
                LocalDataSource.saveUserInfo(context,"email",user.kakaoAccount?.email!!)
                LocalDataSource.saveUserInfo(context,"phone",formatted_phone)
                LocalDataSource.saveUserInfo(context,"birthDate",birthDate.toString())
                LocalDataSource.saveUserInfo(context,"profileImg",user.kakaoAccount?.profile?.thumbnailImageUrl?:"")
                LocalDataSource.saveUserInfo(context,"gender",gender.toString())
                LocalDataSource.saveSignupType(context,SIGNUP_SOCIAL_KAKAO)
                loginViewModel.socialLogin("kakao",user.kakaoAccount?.email!!)
//                loginViewModel.socialLogin(type="kakao",email,name,phoneNumber,gender, birthDate = birthDate.toString(),imageUrl)
            }
        }
    }

    private fun gotoSignupActivity(){
        val intent = Intent(context, SignupActivity::class.java)
        startActivity(intent)
    }

    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    //사용하지 않는 함수들 (나중에 사용할수도..?)
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
                        handleKakaoLogin()
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


    companion object{
        const val USER_INFO="USER_INFO"
        const val USER_NAME="USER_NAME"
        const val SIGNUP_SOCIAL_NAVER="NAVER"
        const val SIGNUP_SOCIAL_KAKAO="KAKAO"
    }

}
