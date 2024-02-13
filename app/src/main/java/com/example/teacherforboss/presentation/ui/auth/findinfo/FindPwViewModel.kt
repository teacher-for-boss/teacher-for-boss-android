package com.example.teacherforboss.presentation.ui.auth.findinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.data.model.request.findInfo.RequestFindEmailDto
import com.example.teacherforboss.data.model.request.signup.EmailCheckRequest
import com.example.teacherforboss.data.model.request.signup.EmailRequest
import com.example.teacherforboss.data.model.request.signup.PhoneCheckRequest
import com.example.teacherforboss.data.model.request.signup.PhoneRequest
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.data.model.response.findInfo.ResponseFindEmailDto
import com.example.teacherforboss.data.model.response.findInfo.ResponseFindPwDto
import com.example.teacherforboss.data.model.response.signup.EmailCheckResponse
import com.example.teacherforboss.data.model.response.signup.EmailResponse
import com.example.teacherforboss.data.model.response.signup.PhoneCheckResponse
import com.example.teacherforboss.data.model.response.signup.PhoneResponse
import com.example.teacherforboss.data.repository.FindInfoRepositoryImpl
import com.example.teacherforboss.data.repository.UserRepositoryImpl
import com.example.teacherforboss.domain.model.SurveyEntity
import com.example.teacherforboss.domain.model.SurveyResultEntity
import com.example.teacherforboss.util.view.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class FindPwViewModel:ViewModel() {
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
    var phoneAuthId=MutableLiveData<Long>(19)

    val matchedEmail=MutableLiveData<String>("")
    val matchedcreatedAt=MutableLiveData<String>("")

    //find email

    val _findEmailResultState=MutableStateFlow<UiState<ResponseFindEmailDto?>>(UiState.Empty)
    val findEmailResultState get() = _findEmailResultState.asStateFlow()

    fun postFindEmail(){
        viewModelScope.launch {
            _findEmailResultState.value=UiState.Loading
            _findEmailResultState.emit(UiState.Loading)

            try{
                val response=findInfoRepo.findEmail(RequestFindEmailDto(phoneAuthId=phoneAuthId.value!!))
                Log.d("findinfo",response?.body().toString())
                if(response?.body()?.code=="COMMON200"){
                    _findEmailResultState.value=UiState.Success(response.body()!!)
                    _findEmailResultState.emit(UiState.Success(response.body())!!)
                }
                else{
                    _findEmailResultState.value=UiState.Error(response?.body()?.message)
                }
            }catch (e:Exception){
                _findEmailResultState.value=(UiState.Error(e.message))
            }

            }
    }

    //find pw

    //phone
    val phoneResult: MutableLiveData<BaseResponse<PhoneResponse>> = MutableLiveData()
    fun phoneUser(phone: String,hash:String) {
        phoneResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val phoneRequest = PhoneRequest(
                    phone = phone,
                    purpose = 2,//이메일 찾기
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

    //email
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


}