package com.example.teacherforboss.presentation.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.data.repository.UserRepositoryImpl
import com.example.teacherforboss.data.model.request.signup.EmailCheckRequest
import com.example.teacherforboss.data.model.response.signup.EmailCheckResponse
import com.example.teacherforboss.data.model.request.signup.EmailRequest
import com.example.teacherforboss.data.model.response.signup.EmailResponse
import com.example.teacherforboss.data.model.request.signup.SignupRequest
import com.example.teacherforboss.data.model.response.signup.SignupResponse
import com.example.teacherforboss.data.model.request.signup.PhoneCheckRequest
import com.example.teacherforboss.data.model.response.signup.PhoneCheckResponse
import com.example.teacherforboss.data.model.request.signup.PhoneRequest
import com.example.teacherforboss.data.model.response.signup.PhoneResponse
import kotlinx.coroutines.launch

class SignupViewModel(
//    private val userRepo: UserRepository
): ViewModel() {
    var liveEmail= MutableLiveData<String>("")
    var livePhone=MutableLiveData<String>("")
    val email: LiveData<String>
        get() = liveEmail
    val phone:LiveData<String>
        get() = livePhone

    var livePw= MutableLiveData<String>("")
    var liveRePw= MutableLiveData<String>("")
    val pw: LiveData<String>
        get() = livePw
    val rePw: LiveData<String>
        get() = liveRePw

    var name:String=""
    var gender:String=""
    var birthDate:String=""
    var emailAuthId:Long=0
    var phoneAuthId:Long=0

    val num_check= MutableLiveData<Boolean>(false)
    val eng_check= MutableLiveData<Boolean>(false)
    val special_check= MutableLiveData<Boolean>(false)
    val length_check= MutableLiveData<Boolean>(false)
    val rePw_check= MutableLiveData<Boolean>(false)
    val all_check= MutableLiveData<Boolean>(false)


    val userRepo= UserRepositoryImpl()

    //이메일인증 여부
    private var _isEmailVerified= MutableLiveData<Boolean>(false)
    val isEmailVerified: LiveData<Boolean>
        get() = _isEmailVerified

    //이메일인증확인 맵
    val confirmedEmail= MutableLiveData<MutableMap<String, LiveData<Boolean>>>()

    //휴대폰인증 여부
    private var _isPhoneVerified=MutableLiveData<Boolean>(false)
    val isPhoneVerified:LiveData<Boolean>
        get()=_isPhoneVerified

    //휴대폰인증확인 맵
    val confirmedPhone= MutableLiveData<MutableMap<String,Boolean>>()


    fun setEmailVerifiedStatus(isVefiried:Boolean){
        _isEmailVerified.value=isVefiried

    }
    fun setPhoneVerifiedStatus(isVefiried: Boolean){
        _isPhoneVerified.value=isVefiried
    }

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
    fun emailCheckUser(emailAuthId:Long,emailAuthCode:String) {
        emailCheckResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val emailCheckRequest = EmailCheckRequest(
                    emailAuthId = emailAuthId,
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
                    phone = phone.value.toString(),
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

//    val helper = AppSignatureHelper(getApplication())
//    val hash = helper.getAppSignatures()?.get(0)
//    Log.d("hash test",hash.toString())

    val phoneResult: MutableLiveData<BaseResponse<PhoneResponse>> = MutableLiveData()
    fun phoneUser(phone: String) {
        phoneResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val phoneRequest = PhoneRequest(
                    phone = phone,
                    purpose = 1,
                    appHash = ""
                )
                val response = userRepo.phoneUser(phoneRequest = phoneRequest)

                if (response?.code() == 200) {
                    phoneResult.value = BaseResponse.Success(response.body())
                } else {
                    phoneResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                phoneResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    val phoneCheckResult: MutableLiveData<BaseResponse<PhoneCheckResponse>> = MutableLiveData()
    fun phoneCheckUser(phoneAuthId: Long, phoneAuthCode: String) {
        phoneCheckResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val phoneCheckRequest = PhoneCheckRequest(
                    phoneAuthId = phoneAuthId,
                    phoneAuthCode = phoneAuthCode
                )
                val response = userRepo.phoneCheck(phoneCheckRequest = phoneCheckRequest)

                if(response?.code() == 200) {
                    phoneCheckResult.value = BaseResponse.Success(response.body())
                } else {
                    phoneCheckResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                phoneCheckResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}