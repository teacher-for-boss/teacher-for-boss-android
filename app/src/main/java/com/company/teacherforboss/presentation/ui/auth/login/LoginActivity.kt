package com.company.teacherforboss.presentation.ui.auth.login

import android.content.Intent
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityLoginBinding
import com.company.teacherforboss.data.model.response.BaseResponse
import com.company.teacherforboss.presentation.ui.auth.findinfo.screens.FindPwActivity
import com.company.teacherforboss.presentation.ui.auth.login.social.SocialLoginUiState
import com.company.teacherforboss.presentation.ui.auth.login.social.SocialLoginViewModel
import com.company.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.base.ConstsUtils
import com.company.teacherforboss.util.base.ConstsUtils.Companion.ACTIVITY_DESTINATION
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_PROFILE_IMG_URL
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIGNUP_SOCIAL_KAKAO
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIGNUP_SOCIAL_NAVER
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_BIRTHDATE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_EMAIL
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_GENDER
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_NAME
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_PHONE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_PROFILEIMG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_ROLE
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.base.LocalDataSource.Companion.FCM_TOKEN
import com.company.teacherforboss.util.base.LocalDataSource.Companion.SOCIAL_MARKETING_EMAIL_AGREEMENT
import com.company.teacherforboss.util.base.LocalDataSource.Companion.SOCIAL_MARKETING_KAKAO
import com.company.teacherforboss.util.base.LocalDataSource.Companion.SOCIAL_MARKETING_SMS_AGREEMENT
import com.google.firebase.messaging.FirebaseMessaging
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
import javax.inject.Inject
import kotlin.reflect.typeOf

@AndroidEntryPoint
class LoginActivity: BindingActivity<ActivityLoginBinding>(R.layout.activity_login){
    private val signupViewModel by viewModels<SignupViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()
    private val socialLoginViewModel by viewModels<SocialLoginViewModel>()
    private val context=this
    @Inject lateinit var localDataSource: LocalDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        localDataSource.deleteUserInfo()
        localDataSource.resetSignupType()

        fetchFirebaseToken()
        askNotificationPermission()

        //기본 로그인
        val token=loginViewModel.getAcessToken()
        if (!token.isNullOrBlank()) { gotoMainActivity() }

        //기본 로그인
        loginViewModel.loginResult.observe(this){
            when(it){
                is BaseResponse.Loading ->{
                    // 로딩창
                }
                is BaseResponse.Success ->{
                    loginViewModel.saveToken(it.data)
                    localDataSource.saveUserInfo(USER_NAME,it.data?.result?.name?:"")
                    localDataSource.saveUserInfo(USER_ROLE,it.data?.result?.role?:"boss")
                    localDataSource.saveUserInfo(USER_EMAIL,it.data?.result?.email!!)
                    gotoMainActivity()
                }
                is BaseResponse.Error ->{
                    CustomSnackBar.make(binding.root, getString(R.string.id_or_pw_different), 2000).show()
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
                            checkKakaoAgreement()
                        }
                        SocialLoginUiState.LoginFail->{
                            CustomSnackBar.make(binding.root, getString(R.string.social_login_fail), 2000).show()
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
                is BaseResponse.Loading ->{CustomSnackBar.make(binding.root,getString(R.string.login_loading),1000).show() }
                is BaseResponse.Success ->{
                    loginViewModel.saveToken(it.data)
                    localDataSource.saveUserInfo(USER_NAME,it.data?.result?.name!!.toString())
                    localDataSource.saveUserInfo(USER_ROLE,it.data?.result?.role?:"boss")
                    gotoMainActivity()
                }
                is BaseResponse.Error ->{
                    // 회원가입 진행
                    CustomSnackBar.make(binding.root,getString(R.string.login_loading),1000).show()
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

        // TODO: 깨져서 주석함, 앱 심사 후 권한 요청
        binding.kakaoBtn.setOnClickListener {
            socialLoginViewModel.kakaoLogin()
        }
        binding.naverBtn.setOnClickListener {
            socialLoginViewModel.naverLogin()
        }


        binding.findEmailBtn.setOnClickListener {
            val intent=Intent(this, FindPwActivity::class.java)
            intent.putExtra(ACTIVITY_DESTINATION,"email")
            startActivity(intent)
        }

        binding.findPwBtn.setOnClickListener {
            val intent=Intent(this,FindPwActivity::class.java)
            intent.putExtra(ACTIVITY_DESTINATION,"pw")
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

                        var gender_int=3
                        //사용자 정보 전처리
                        if(gender=="M")gender_int=1
                        else if(gender=="F") gender_int=2

                        signupViewModel._birthDate.value=birthYear+"-"+birthDay

                        localDataSource.saveUserInfo(USER_NAME,result.profile?.name.toString())
                        localDataSource.saveUserInfo(USER_EMAIL,result.profile?.email.toString())
                        localDataSource.saveUserInfo(USER_PHONE,result.profile?.mobile.toString().replace("-",""))

                        if(birthDay!="null" && birthYear!="null") localDataSource.saveUserInfo(USER_BIRTHDATE,birthYear+"-"+birthDay)
                        if(imageUrl == "null") {
                            localDataSource.saveUserInfo(USER_PROFILEIMG, DEFAULT_PROFILE_IMG_URL)
                        } else {
                            if(imageUrl.contains("img_profile")) {
                                localDataSource.saveUserInfo(USER_PROFILEIMG, DEFAULT_PROFILE_IMG_URL)
                            }
                            else {
                                localDataSource.saveUserInfo(USER_PROFILEIMG,imageUrl)
                            }
                        }
                       localDataSource.saveUserInfo(USER_GENDER,gender_int.toString())

                        Log.e("naver", "네이버 로그인한 유저 정보 - 이름 : $name")
                        Log.e("naver", "네이버 로그인한 유저 정보 - 이메일 : $email")
                        Log.e("naver", "네이버 로그인한 유저 정보 - 성별 : $gender")
                        Log.e("naver", "네이버 로그인한 유저 정보 - 폰번호 : $phoneNumber")
                        Log.e("naver", "네이버 로그인한 유저 정보 - 생년 월일 : $birthYear+${birthDay}")
                        Log.e("naver", "네이버 로그인한 유저 정보 - 이미지 : $imageUrl")

                        // 소셜 로그인 요청
                        localDataSource.saveSignupType(SIGNUP_SOCIAL_NAVER)
                        signupViewModel._socialType.value=3
                        loginViewModel.socialLogin(type="naver",email)
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
        var gender: Int
        var birthDate_str:String?=""

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
                if(user.kakaoAccount?.gender.toString()=="MALE"){ gender = 1 }
                else if(user.kakaoAccount?.gender.toString()=="FEMALE"){ gender = 2 }
                else { gender = 3 }

                if(user.kakaoAccount?.birthyear!=null && user.kakaoAccount?.birthday!=null) {
                    birthDate_str=user.kakaoAccount?.birthyear.toString()+user.kakaoAccount?.birthday.toString()
                    val formatter=DateTimeFormatter.ofPattern("yyyyMMdd")
                    val birthDate=LocalDate.parse(birthDate_str,formatter)
                    localDataSource.saveUserInfo(USER_BIRTHDATE,birthDate.toString())

                }
                val formatted_phone=user.kakaoAccount?.phoneNumber!!
                    .replace("+82","0")
                    .replace("-","")
                    .replace(" ","")

                localDataSource.saveUserInfo(USER_NAME,user.kakaoAccount?.name!!)
                localDataSource.saveUserInfo(USER_EMAIL,user.kakaoAccount?.email!!)
                localDataSource.saveUserInfo(USER_PHONE,formatted_phone)

                if(user.kakaoAccount?.profile?.thumbnailImageUrl == null) {
                    localDataSource.saveUserInfo(USER_PROFILEIMG, DEFAULT_PROFILE_IMG_URL)
                } else {
                    if(user.kakaoAccount?.profile?.thumbnailImageUrl!!.contains("default_profile")) {
                        localDataSource.saveUserInfo(USER_PROFILEIMG, DEFAULT_PROFILE_IMG_URL)
                    } else {
                        localDataSource.saveUserInfo(USER_PROFILEIMG,user.kakaoAccount?.profile?.thumbnailImageUrl!!)
                    }
                }
                localDataSource.saveUserInfo(USER_GENDER,gender.toString())
                localDataSource.saveSignupType(SIGNUP_SOCIAL_KAKAO)
                loginViewModel.socialLogin(SIGNUP_SOCIAL_KAKAO,user.kakaoAccount?.email!!)
//                loginViewModel.socialLogin(type="kakao",email,name,phoneNumber,gender, birthDate = birthDate.toString(),imageUrl)
            }
        }
    }

    private fun checkKakaoAgreement(){
        // 서비스 약관 동의 내역 확인하기
        UserApiClient.instance.serviceTerms { userServiceTerms, error ->
            if (error != null) {
                Log.e(TAG, "서비스 약관 동의 내역 확인하기 실패", error)
            } else if (userServiceTerms != null) {
                Log.i(
                    TAG, "서비스 약관 동의 내역 확인하기 성공" +
                            "\n회원번호: ${userServiceTerms.id}" +
                            "\n동의한 약관: \n${userServiceTerms.serviceTerms?.joinToString("\n")}"
                )
                val termsTags = userServiceTerms.serviceTerms?.map { it.tag }

                var AgreementMarketingKakao = false
                var AgreementMarketingInfoEmail = false
                var AgreementMarketingInfoSms = false

                if(termsTags!!.contains("AgreementMarketingInfoKakao")) { AgreementMarketingKakao = true }
                if(termsTags!!.contains("AgreementMarketingInfoEmail")) { AgreementMarketingInfoEmail = true }
                if(termsTags!!.contains("AgreementMarketingInfoSms")) { AgreementMarketingInfoSms = true }

                localDataSource.saveMarketingAgreementStatus(SOCIAL_MARKETING_KAKAO, AgreementMarketingKakao)
                localDataSource.saveMarketingAgreementStatus(SOCIAL_MARKETING_SMS_AGREEMENT,AgreementMarketingInfoSms)
                localDataSource.saveMarketingAgreementStatus(SOCIAL_MARKETING_EMAIL_AGREEMENT, AgreementMarketingInfoEmail)
            }
        }
    }

    private fun gotoSignupActivity(){
        val intent = Intent(context, SignupActivity::class.java)
        startActivity(intent)
    }

    private fun gotoMainActivity(){
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
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

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API Level 33 이상
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            fetchFirebaseToken()
        }
    }

   // fcm messaging 권한 요청
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun fetchFirebaseToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    localDataSource.saveUserInfo(FCM_TOKEN, token)
                    Log.d("FCM Log login", "New FCM token: $token")
                } else {
                    Log.e("FCM Log login", "Failed to get new FCM token", task.exception)
                }
            }
    }
}
