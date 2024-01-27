package com.example.teacherforboss.presentation.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.presentation.ui.auth.common.BaseResponse
import com.example.teacherforboss.presentation.ui.auth.common.UserRepository
import com.example.teacherforboss.presentation.ui.auth.signup.api.EmailCheckRequest
import com.example.teacherforboss.presentation.ui.auth.signup.api.EmailCheckResponse
import com.example.teacherforboss.presentation.ui.auth.signup.api.EmailRequest
import com.example.teacherforboss.presentation.ui.auth.signup.api.EmailResponse
import com.example.teacherforboss.presentation.ui.auth.signup.api.SignupRequest
import com.example.teacherforboss.presentation.ui.auth.signup.api.SignupResponse
import kotlinx.coroutines.launch

class SignupViewModel: ViewModel() {
    var liveEmail= MutableLiveData<String>("")
    val email: LiveData<String>
        get() = liveEmail

    var livePw= MutableLiveData<String>("")
    var liveRePw= MutableLiveData<String>("")
    val pw: LiveData<String>
        get() = livePw
    val rePw: LiveData<String>
        get() = liveRePw

    var name:String=""
    var gender:String=""
    var birthDate:String=""
    var phone:String=""//추후 api 연결하며 email 방식대로 수정
    var emailAuthId:Long=0
    var phoneAuthId:Long=0

    val num_check= MutableLiveData<Boolean>(false)
    val eng_check= MutableLiveData<Boolean>(false)
    val special_check= MutableLiveData<Boolean>(false)
    val length_check= MutableLiveData<Boolean>(false)
    val rePw_check= MutableLiveData<Boolean>(false)
    val all_check= MutableLiveData<Boolean>(false)


    //이메일인증 여부
    private var _isEmailVerified= MutableLiveData<Boolean>(false)
    val isEmailVerified: LiveData<Boolean>
        get() = _isEmailVerified

    //이메일인증확인 맵
    val confirmedEmail= MutableLiveData<MutableMap<String, LiveData<Boolean>>>()

    //휴대폰인증
    private var _isPhoneVerified= MutableLiveData<Boolean>(false)
    val isPhoneVerified: LiveData<Boolean>
        get()=_isPhoneVerified

    //휴대폰인증확인 맵
    val confirmedPhone= MutableLiveData<MutableMap<String,Boolean>>()


    fun setEmailVerifiedStatus(isVefiried:Boolean){
        _isEmailVerified.value=isVefiried

    }
    fun setPhoneVerifiedStatus(isVefiried: Boolean){
        _isPhoneVerified.value=isVefiried
    }



    val userRepo= UserRepository()

    val emailResult: MutableLiveData<BaseResponse<EmailResponse>> = MutableLiveData()
    fun emailUser(email:String) {
        emailResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val emailRequest = EmailRequest(
                    email = email,
                    purpose = 1  //회원가입을 위한 이메일 입력
                )
                val response = userRepo.emailUser(emailRequest = emailRequest)

                if (response?.code() == 200) {
                    emailResult.value = BaseResponse.Success(response.body())
                } else {
                    emailResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                emailResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    val emailCheckResult: MutableLiveData<BaseResponse<EmailCheckResponse>> = MutableLiveData()
    fun emailCheckUser(emailAuthId:Int,emailAuthCode:String) {
        emailCheckResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val emailCheckRequest = EmailCheckRequest(
                    emailAuthId = emailAuthId,
                    emailAuthCode = emailAuthCode
                    //이메일인증객체id에는 뭘 넣어줘야하는지?-> /auth/email 시 response 받은 값 넣어주기
                )
                val response = userRepo.emailCheck(emailCheckRequest = emailCheckRequest)

                if (response?.code() == 200) {
                    emailCheckResult.value = BaseResponse.Success(response.body())
                } else {
                    emailCheckResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                emailCheckResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    val signupResult: MutableLiveData<BaseResponse<SignupResponse>> = MutableLiveData()

    //isChecked, emailAuth, phoneAuth 파라미터로 넣어야하는지?
//    fun signupUser(email:String, isChecked:String,password:String, rePassword:String, name:String,
//                   gender:String, birthDate:String, phone:String,emailAuthId:Long,phoneAuthId:Long)
//
    //viewmodel 값으로 직접 넣는것으로 수정중
    fun signupUser()
    {
        signupResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
//                val signupRequest = SignupRequest(
//                    email = email,
//                    isChecked = isChecked, //이메일인증여부,
//                    password = password,
//                    rePassword = rePassword,
//                    name = name,
//                    gender = gender,
//                    birthDate = birthDate,
//                    phone = phone,
//                    emailAuthId = emailAuthId,//이메일인증식별자,
//                    phoneAuthId = phoneAuthId //전화번호인증식별자
//                )
                val signupRequest = SignupRequest(
                    email = email.value.toString(),
                    isChecked = isEmailVerified.value.toString(), //이메일인증여부,
                    password = pw.value.toString(),
                    rePassword = rePw.value.toString(),
                    name = name,
                    gender = gender,
                    birthDate = birthDate,
                    phone = phone,
                    emailAuthId = emailAuthId,//이메일인증식별자,
                    phoneAuthId = phoneAuthId //전화번호인증식별자
                )
                val response = userRepo.signupUser(signupRequest = signupRequest)

                if (response?.code() == 200) {
                    signupResult.value = BaseResponse.Success(response.body())
                } else {
                    signupResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                signupResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}