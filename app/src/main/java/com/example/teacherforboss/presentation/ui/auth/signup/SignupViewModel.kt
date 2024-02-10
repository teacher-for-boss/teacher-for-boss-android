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
import com.example.teacherforboss.domain.model.SignupEntity
import com.example.teacherforboss.domain.model.SignupResultEntity
import com.example.teacherforboss.domain.usecase.SignupUseCase
import com.example.teacherforboss.util.view.UiState
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class SignupViewModel(
//@Inject constructor(
//    private val userRepo: UserRepository
//    private val signupUseCase: SignupUseCase

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

    var _name=MutableLiveData<String>("")
    val name:LiveData<String>
        get() = _name

    var _gender=MutableLiveData<Int>(3)
    val gender:LiveData<Int>
        get() = _gender

    var _birthDate=MutableLiveData<String>("")
    val birthDate:LiveData<String>
        get() = _birthDate


    var emailAuthId=MutableLiveData<Long>(1) //test용 원래는 0으로 설정
    var phoneAuthId=MutableLiveData<Long>(1)

    val num_check= MutableLiveData<Boolean>(false)
    val eng_check= MutableLiveData<Boolean>(false)
    val special_check= MutableLiveData<Boolean>(false)
    val length_check= MutableLiveData<Boolean>(false)
    val rePw_check= MutableLiveData<Boolean>(false)
    val all_check= MutableLiveData<Boolean>(false)

    //약관 동의
    val agreementUsage=MutableLiveData<String>("F")
    val agreementInfo=MutableLiveData<String>("F")
    val agreementAge=MutableLiveData<String>("F")
    val agreementSms=MutableLiveData<String>("F")
    val agreementEmail=MutableLiveData<String>("F")
    val agreementLocation=MutableLiveData<String>("F")

    val userRepo= UserRepositoryImpl()

    //이메일인증 여부 str->api
    var _isEmailVerified_str= MutableLiveData<String>("T") // api 테스트 완료 추후 false로 수정
    val isEmailVerified_str: LiveData<String>
        get() = _isEmailVerified_str

    //이메일인증 여부 boolean ->data binding
    var _isEmailVerified= MutableLiveData<Boolean>(true)
    val isEmailVerified: LiveData<Boolean>
        get() = _isEmailVerified

    //이메일인증확인 맵
    val confirmedEmail= MutableLiveData<MutableMap<String, LiveData<Boolean>>>()

    //휴대폰인증 여부 str->api
    var _isPhoneVerified_str=MutableLiveData<String>("T") // api 테스트 완료 추후 false로 수정
    val isPhoneVerified_str:LiveData<String>
        get()=_isPhoneVerified_str

    //휴대폰 인증 여부 boolean->data binding
    var _isPhoneVerified=MutableLiveData<Boolean>(true)
    val isPhoneVerified:LiveData<Boolean>
        get()=_isPhoneVerified

    //휴대폰인증확인 맵
    val confirmedPhone= MutableLiveData<MutableMap<String,Boolean>>()

//
//    fun setEmailVerifiedStatus(isVefiried:Boolean){
//        _isEmailVerified.value=isVefiried
//
//    }
//    fun setPhoneVerifiedStatus(isVefiried: Boolean){
//        _isPhoneVerified.value=isVefiried
//    }

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

                if (response?.body()?.code=="COMMON200") {
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

                if (response?.body()?.code=="COMMON200") {
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

    fun signupUser()
    {
        signupResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val signupRequest = SignupRequest(
                    email = email.value.toString(),
                    password = pw.value.toString(),
                    rePassword = rePw.value.toString(),
                    name = name.value.toString(),
                    gender = gender.value!!,
                    birthDate = birthDate.value.toString(),
                    phone = phone.value.toString(),
                    emailAuthId = emailAuthId.value!!,//이메일인증식별자,
                    phoneAuthId = phoneAuthId.value!!, //전화번호인증식별자
                    agreementUsage = agreementUsage.value!!,
                    agreementInfo=agreementInfo.value!!,
                    agreementAge=agreementAge.value!!,
                    agreementSms=agreementSms.value!!,
                    agreementEmail=agreementEmail.value!!,
                    agreementLocation=agreementLocation.value!!
                )
                val response = userRepo.signupUser(signupRequest = signupRequest)

                if (response?.body()?.code=="COMMON200") {
                    signupResult.value = BaseResponse.Success(response.body())
                } else {
                    signupResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                signupResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    //ver.지은님 코드 signup
    private val _signupResultState= MutableSharedFlow<UiState<SignupResultEntity>>()
    val signupResultState get() = _signupResultState.asSharedFlow()
//    fun signup(){
//        viewModelScope.launch {
//            _signupResultState.emit(UiState.Loading)
//            runCatching {
//                signupUseCase(
//                    signupEntity = SignupEntity(
//                        email=email.value!!,
//                        isChecked = isEmailVerified_str.value!!,
//                        password = pw.value!!,
//                        rePassword = rePw.value!!,
//                        name=name.value!!,
//                        phone=phone.value!!,
//                        gender=gender.value!!,
//                        birthDate=birthDate.value!!,
//                        emailAuthId=emailAuthId.value!!,
//                        phoneAuthId = phoneAuthId.value!!,
//                        agreementUsage = agreementUsage.value!!,
//                        agreementInfo = agreementInfo.value!!,
//                        agreementAge = agreementAge.value!!,
//                        agreementEmail = agreementEmail.value!!,
//                        agreementLocation = agreementLocation.value!!,
//                        agreementSms = agreementSms.value!!
//                    )
//
//                ).collect(){data->
//                    _signupResultState.emit((UiState.Success(data)))
//                }
//
//        }.onFailure { ex:Throwable->
//            _signupResultState.emit(UiState.Error(ex.message))
//        }
//    }
//    }


    val phoneResult: MutableLiveData<BaseResponse<PhoneResponse>> = MutableLiveData()
    fun phoneUser(phone: String,hash:String) {
        phoneResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val phoneRequest = PhoneRequest(
                    phone = phone,
                    purpose = 1,
                    appHash = hash
                )
                val response = userRepo.phoneUser(phoneRequest = phoneRequest)

                if (response?.body()?.code=="COMMON200") {
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

                if(response?.body()?.code=="COMMON200") {
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