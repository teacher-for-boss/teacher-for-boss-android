package com.example.teacherforboss.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.login.BaseResponse
import com.example.teacherforboss.login.UserRepository
import kotlinx.coroutines.launch

class SignupViewModel:ViewModel() {
    var liveEmail=MutableLiveData<String>("")
    var livePw=MutableLiveData<String>("")
    var liveRePw=MutableLiveData<String>("")

    val num_check=MutableLiveData<Boolean>(false)
    val eng_check=MutableLiveData<Boolean>(false)
    val special_check=MutableLiveData<Boolean>(false)
    val length_check=MutableLiveData<Boolean>(false)
    val rePw_check=MutableLiveData<Boolean>(false)
    val all_check=MutableLiveData<Boolean>(false)


    //이메일인증
    private var _isEmailVerified=MutableLiveData<Boolean>(false)
    val isEmailVerified:LiveData<Boolean>
        get() = _isEmailVerified

    //이메일인증확인
    val confirmedEmail=MutableLiveData<MutableMap<String,LiveData<Boolean>>>()

    //휴대폰인증
    private var _isPhoneVerified=MutableLiveData<Boolean>(false)
    val isPhoneVerified:LiveData<Boolean>
        get()=_isPhoneVerified

    //휴대폰인증확인
    val confirmedPhone=MutableLiveData<MutableMap<String,Boolean>>()


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
    fun emailCheckUser(emailAuthCode:String) {
        emailCheckResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val emailCheckRequest = EmailCheckRequest(
                    emailAuthId = //이메일인증객체id에는 뭘 넣어줘야하는지?,
                    emailAuthCode = emailAuthCode
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
    fun signupUser(email:String, password:String, rePassword:String, name:String,
                   gender:String, birthDate:String, phone:String) {
        signupResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val signupRequest = SignupRequest(
                    email = email,
                    isChecked = //이메일인증여부,
                    password = password,
                    rePassword = rePassword,
                    name = name,
                    gender = gender,
                    birthDate = birthDate,
                    phone = phone,
                    emailAuth = //이메일인증식별자,
                    phoneAuthId = //전화번호인증식별자
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