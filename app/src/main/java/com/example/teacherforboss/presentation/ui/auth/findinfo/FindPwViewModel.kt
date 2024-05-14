package com.example.teacherforboss.presentation.ui.auth.findinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.data.model.request.findInfo.RequestFindEmailDto
import com.example.teacherforboss.data.model.request.findInfo.RequestFindPwDto
import com.example.teacherforboss.data.model.request.findInfo.RequestResetPwDto
import com.example.teacherforboss.data.model.request.signup.EmailCheckRequest
import com.example.teacherforboss.data.model.request.signup.EmailRequest
import com.example.teacherforboss.data.model.request.signup.PhoneCheckRequest
import com.example.teacherforboss.data.model.request.signup.PhoneRequest
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.data.model.response.findInfo.ResponseFindEmailDto
import com.example.teacherforboss.data.model.response.findInfo.ResponseFindPwDto
import com.example.teacherforboss.data.model.response.findInfo.ResponseResetPwDto
import com.example.teacherforboss.data.model.response.signup.EmailCheckResponse
import com.example.teacherforboss.data.model.response.signup.EmailResponse
import com.example.teacherforboss.data.model.response.signup.PhoneCheckResponse
import com.example.teacherforboss.data.model.response.signup.PhoneResponse
import com.example.teacherforboss.data.repository.FindInfoRepositoryImpl
import com.example.teacherforboss.data.repository.UserRepositoryImpl
import com.example.teacherforboss.util.Timer.Custom3mTimer
import com.example.teacherforboss.util.base.ErrorUtils
import com.example.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindPwViewModel @Inject constructor(
    private val Emailtimer: Custom3mTimer,
    private val Pwtimer : Custom3mTimer
):ViewModel() {
    val userRepo= UserRepositoryImpl()
    val findInfoRepo= FindInfoRepositoryImpl()

    var livePhoneNumber=MutableLiveData<String>("")
    val phoneNumber:LiveData<String>
        get() = livePhoneNumber

    var liveEmail=MutableLiveData<String>("")
    val email: LiveData<String>
        get() = liveEmail

    var livePw= MutableLiveData<String>("")
    var liveRePw= MutableLiveData<String>("")
    val pw: LiveData<String>
        get() = livePw
    val rePw: LiveData<String>
        get() = liveRePw

    var _isPhoneVerified=MutableLiveData<Boolean>(false)
    val isPhoneVerified:LiveData<Boolean>
        get() = _isPhoneVerified

    var _isEmailVerified=MutableLiveData<Boolean>(false)
    val isEmailVerified:LiveData<Boolean>
        get() = _isEmailVerified

    val num_check= MutableLiveData<Boolean>(false)
    val eng_check= MutableLiveData<Boolean>(false)
    val special_check= MutableLiveData<Boolean>(false)
    val length_check= MutableLiveData<Boolean>(false)
    val rePw_check= MutableLiveData<Boolean>(false)
    val all_check= MutableLiveData<Boolean>(false)

    val phone_check=MutableLiveData<Boolean>(false)

    val email_check=MutableLiveData<Boolean>(true)

    var emailAuthId=MutableLiveData<Long>(1) //test용 원래는 0으로 설정
    var phoneAuthId=MutableLiveData<Long>(0)

    var memberId=MutableLiveData<Long>(0)

    val matchedEmail=MutableLiveData<String>("")
    val matchedcreatedAt=MutableLiveData<String>("")

    // 1. auth/find/email

    val _findEmailResultState=MutableStateFlow<UiState<ResponseFindEmailDto?>>(UiState.Empty)
    val findEmailResultState get() = _findEmailResultState.asStateFlow()
    fun postFindEmail(){
        viewModelScope.launch {
            _findEmailResultState.value=UiState.Loading
            _findEmailResultState.emit(UiState.Loading)

            try{
                val response=findInfoRepo.findEmail(RequestFindEmailDto(phoneAuthId=phoneAuthId.value!!))
                if(response?.code=="COMMON200"){
//                    _findEmailResultState.value=UiState.Success(response?.result)
                    _findEmailResultState.emit(UiState.Success(response?.result))
                }
                else{
                    _findEmailResultState.value=UiState.Error(response?.message)
                }
            }catch (e:Exception){
                _findEmailResultState.value=(UiState.Error(e.message))
            }

            }
    }



    //1-1.auth/phone
    val phoneResult: MutableLiveData<BaseResponse<PhoneResponse>> = MutableLiveData()
    fun phoneUser(hash:String) {
        phoneResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val phoneRequest = PhoneRequest(
                    phone = phoneNumber.value.toString(),
                    purpose = 2,//이메일 찾기
                    appHash = hash
                )
                val response = userRepo.phoneUser(phoneRequest = phoneRequest)

                if (response?.body()?.code=="COMMON200") {
                    phoneResult.value = BaseResponse.Success(response.body())
                } else {
                    val errorbody=ErrorUtils.getErrorResponse(response?.errorBody()!!)
                    phoneResult.value = BaseResponse.Error(errorbody.message)
                }
            } catch (ex: Exception) {
                phoneResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    //1-2. auth/phone/check
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
                    val errorbody=ErrorUtils.getErrorResponse(response?.errorBody()!!)
                    phoneCheckResult.value = BaseResponse.Error(errorbody.message)
                }
            } catch (ex: Exception) {
                phoneCheckResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    //2. auth/find/password

    val _findpwResultState=MutableStateFlow<UiState<ResponseFindPwDto?>>(UiState.Empty)
    val findpwResultState get() = _findpwResultState.asStateFlow()
    fun postFindPw(){
        viewModelScope.launch {
            _findpwResultState.emit(UiState.Loading)

            try{
                val response=findInfoRepo.findPw(RequestFindPwDto(emailAuthId=emailAuthId.value!!))
                if(response?.code=="COMMON200"){
                    _findpwResultState.emit(UiState.Success(response.result))
                }
                else{
                    _findpwResultState.value=UiState.Error(response?.message)
                }
            }catch (e:Exception){
                _findpwResultState.value=(UiState.Error(e.message))
            }

        }
    }



    //2-1. auth/email
    val emailResult: MutableLiveData<BaseResponse<EmailResponse>> = MutableLiveData()
    fun emailUser() {
        emailResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val emailRequest = EmailRequest(
                    email = email.value.toString(),
                    purpose = 3  //비밀번호 찾기
                )
                val response = userRepo.emailUser(emailRequest = emailRequest)

                if (response?.body()?.code=="COMMON200") {
                    emailResult.value = BaseResponse.Success(response.body())
                }
                else {
                    val errorbody=ErrorUtils.getErrorResponse(response?.errorBody()!!)
                    phoneResult.value = BaseResponse.Error(errorbody.message)
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
                    emailAuthId = emailAuthId.value!!,
                    emailAuthCode = emailAuthCode
                )
                val response = userRepo.emailCheck(emailCheckRequest = emailCheckRequest)

                if (response?.body()?.code=="COMMON200") {
                    emailCheckResult.value = BaseResponse.Success(response.body())
                } else {
                    val errorbody=ErrorUtils.getErrorResponse(response?.errorBody()!!)
                    emailCheckResult.value = BaseResponse.Error(errorbody.message)
                }
            } catch (ex: Exception) {
                emailCheckResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    // 3. auth/resetPw
    val _resetPwResultState= MutableStateFlow<UiState<ResponseResetPwDto?>>(UiState.Empty)
    val resetPwResultState get() = _resetPwResultState.asStateFlow()

    fun postResetPw(){
        viewModelScope.launch {
            _resetPwResultState.emit(UiState.Loading)

            try{
                val response=findInfoRepo.resetPw(RequestResetPwDto(memberId.value!!,pw.value.toString()!!,rePw.value.toString()!!))
                if(response?.code=="COMMON200"){
                    _resetPwResultState.emit(UiState.Success(response.result))
                }else{
                    _resetPwResultState.emit(UiState.Error(response?.message))
                }
            }catch (e:Exception){
                _resetPwResultState.emit(UiState.Error(e.message))
            }
        }
    }

    //timer
    private val _EmailtimerText=MutableLiveData<String>()
    val EmailtimerText:LiveData<String>
        get() = _EmailtimerText

    private val _EmailtimeOverState=MutableLiveData<Boolean>(false)
    val EmailtimeOverState:LiveData<Boolean>
        get() = _EmailtimeOverState

    fun startEmailTimer(){
        Emailtimer.startTimer { timeLeft,state->
            _EmailtimerText.value=timeLeft
            if (state==true){
                _EmailtimeOverState.value=true
            }
        }
    }

    // stop
    fun stopEmailTimer() {
        Emailtimer.stopTimer()
    }


    private val _PwtimerText=MutableLiveData<String>()
    val PwtimerText:LiveData<String>
        get() = _PwtimerText

    private val _PwtimeOverState=MutableLiveData<Boolean>(false)
    val PwtimeOverState:LiveData<Boolean>
        get() = _PwtimeOverState
    fun startPwTimer(){
        Pwtimer.startTimer { timeLeft,state->
            _PwtimerText.value=timeLeft
            if (state==true){
                _PwtimeOverState.value=true
            }
        }
    }

    fun stopPwTimer() {
        Pwtimer.stopTimer()
    }

}