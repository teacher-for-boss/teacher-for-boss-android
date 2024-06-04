package com.example.teacherforboss.presentation.ui.auth.signup

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.data.model.request.signup.BusinessNumberCheckRequest
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.data.repository.UserRepositoryImpl
import com.example.teacherforboss.data.model.request.signup.EmailCheckRequest
import com.example.teacherforboss.data.model.response.signup.EmailCheckResponse
import com.example.teacherforboss.data.model.request.signup.EmailRequest
import com.example.teacherforboss.data.model.request.signup.NicknameRequest
import com.example.teacherforboss.data.model.response.signup.EmailResponse
import com.example.teacherforboss.data.model.response.signup.SignupResponse
import com.example.teacherforboss.data.model.request.signup.PhoneCheckRequest
import com.example.teacherforboss.data.model.response.signup.PhoneCheckResponse
import com.example.teacherforboss.data.model.request.signup.PhoneRequest
import com.example.teacherforboss.data.model.response.signup.NicknameResponse
import com.example.teacherforboss.data.model.request.signup.SignupBossRequest
import com.example.teacherforboss.data.model.request.signup.SignupTeacherRequest
import com.example.teacherforboss.data.model.response.signup.BusinessNumberCheckResponse
import com.example.teacherforboss.data.model.response.signup.PhoneResponse
import com.example.teacherforboss.domain.model.SignupResultEntity
import com.example.teacherforboss.domain.model.getPresingedUrlEntity
import com.example.teacherforboss.domain.model.presignedUrlListEntity
import com.example.teacherforboss.domain.usecase.PresignedUrlUseCase
import com.example.teacherforboss.util.Timer.Custom10mTimer
import com.example.teacherforboss.util.Timer.Custom3mTimer
import com.example.teacherforboss.util.base.ErrorUtils
import com.example.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    val timer: Custom3mTimer,
    private val presignedUrlUseCase: PresignedUrlUseCase
//    private val userRepo: UserRepository

): ViewModel() {

    // 피봇 이후 회원가입 변수들
    var _field=MutableLiveData<String>("")
    val field:LiveData<String>
        get() = _field

    var _carrer_str=MutableLiveData<String>("")
    val career_str:LiveData<String>
        get() = _carrer_str

    var _career=MutableLiveData<Int>(0)
    val career:LiveData<Int>
        get() = _career

    var _introduction=MutableLiveData<String>("")
    val introduction:LiveData<String>
        get() = _introduction
    var _keywords=MutableLiveData<MutableList<String>>()
    val keywords:LiveData<MutableList<String>>
        get() = _keywords

    val _role=MutableLiveData<Int>(1)
    val role: LiveData<Int>
        get()=_role
    var _nickname=MutableLiveData<String>("")
    val nickname: LiveData<String>
        get()=_nickname
    // boss 변수들

    var _isUserImgSelected=MutableLiveData<Boolean>(false)
    val isUserImgSelectd:LiveData<Boolean>
        get() = _isUserImgSelected

    var _profileImg=MutableLiveData<String>("")
    val profileImg: LiveData<String>
        get() = _profileImg

    var _profileImgUri=MutableLiveData<Uri>(null)
    val profileImgUri: LiveData<Uri>
        get() = _profileImgUri

    // teacher 변수들
    var _businessNum=MutableLiveData<String>("")
    val businessNum:LiveData<String>
        get() = _businessNum

    var _businessNumCheck=MutableLiveData<Boolean>(false)
    val businessNumCheck:LiveData<Boolean>
        get() = _businessNumCheck

    var _representative=MutableLiveData<String>("")
    val representative:LiveData<String>
        get() = _representative

    var _openDate=MutableLiveData<LocalDate>()
    val openDate:LiveData<LocalDate>
        get() = _openDate

    var _openDateStr=MutableLiveData<String>("YYYY-MM-DD")
    val openDate_str:LiveData<String>
        get() = _openDateStr

    var _isBusinessVerified=MutableLiveData<Boolean>(false) //TODO: false로 변경
    val isBusinessVerified:LiveData<Boolean>
        get() = _isBusinessVerified

    var _bank=MutableLiveData<String>("")
    val bank:LiveData<String>
        get() = _bank

    var _accountNum=MutableLiveData<String>("")
    val accountNum:LiveData<String>
        get() = _accountNum

    var _accountHoler=MutableLiveData<String>("")
    val accountHoler:LiveData<String>
        get() = _accountHoler

    // activity page 관련
    var _currentPage=MutableLiveData<Float>(DEFAULT_PROGRESSBAR)
    val currentPage:LiveData<Float>
        get() = _currentPage

    var _totalPage=MutableLiveData<Float>(BOSS_FRAGMENT_SIZE)
    val totalPage: LiveData<Float>
        get() = _totalPage

    // 피봇 이전 회원가입 변수들
    var liveEmail= MutableLiveData<String>("")
    var livePhone=MutableLiveData<String>("")

//    var phone=MutableLiveData<String>("")
    var livePhoneLength=MutableLiveData<Int>(0)
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

    val email_check=MutableLiveData<Boolean>(false)
    val phone_check=MutableLiveData<Boolean>(false)

    //약관 동의
    val agreementUsage=MutableLiveData<String>("F")
    val agreementInfo=MutableLiveData<String>("F")
    val agreementAge=MutableLiveData<String>("F")
    val agreementSms=MutableLiveData<String>("F")
    val agreementEmail=MutableLiveData<String>("F")
    val agreementLocation=MutableLiveData<String>("F")

    val userRepo= UserRepositoryImpl()

    //이메일인증 여부 str->api
    var _isEmailVerified_str= MutableLiveData<String>("F") // api 테스트 완료 추후 false로 수정
    val isEmailVerified_str: LiveData<String>
        get() = _isEmailVerified_str

    //이메일인증 여부 boolean ->data binding
    var _isEmailVerified= MutableLiveData<Boolean>(false) //TODO
    val isEmailVerified: LiveData<Boolean>
        get() = _isEmailVerified

    //이메일인증확인 맵
    val confirmedEmail= MutableLiveData<MutableMap<String, LiveData<Boolean>>>()

    //휴대폰인증 여부 str->api
    var _isPhoneVerified_str=MutableLiveData<String>("F") // api 테스트 완료 추후 false로 수정
    val isPhoneVerified_str:LiveData<String>
        get()=_isPhoneVerified_str

    //휴대폰 인증 여부 boolean->data binding
    var _isPhoneVerified=MutableLiveData<Boolean>(false) //TODO
    val isPhoneVerified:LiveData<Boolean>
        get()=_isPhoneVerified

    //pw체크

    //pw check 정규식
    val num_regex:Regex=Regex("[0-9]+")
    val eng_regex:Regex=Regex("[a-zA-z]+")
    val special_regex:Regex= Regex("[^a-zA-Z0-9가-힣]+")
    fun pw_validation(){
        var pw=livePw.value.toString()
        eng_check.value= (eng_regex.find(pw)!=null)
        num_check.value=(num_regex.find(pw)!=null)
        special_check.value=(special_regex.find(pw)!=null)
        length_check.value=(pw.length>8 && pw.length<20)
        all_check.value=(eng_check.value!!&& num_check.value!! && special_check.value!!&& length_check.value!!)
    }

    //phone 형식 체크
    fun phone_validation(){
        val pattern= Pattern.compile("010\\d{4}\\d{4}")
        phone_check.value=pattern.matcher(livePhone.value.toString()).matches()

    }

    // 사업자 번호 체크
     fun bn_validation(){
        val pattern=Pattern.compile("^\\d{3}-\\d{2}-\\d{5}$")
        _businessNumCheck.value=pattern.matcher(businessNum.value.toString()).matches()
        Log.d("bn",businessNumCheck.value.toString())
    }


    val emailResult: MutableLiveData<BaseResponse<EmailResponse>> = MutableLiveData()
    fun emailUser() {
        emailResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val emailRequest = EmailRequest(
                    email = email.value.toString(),
                    purpose = 1  //회원가입을 위한 이메일 입력
                )
                val response = userRepo.emailUser(emailRequest = emailRequest)

                if(response?.code()==200){
                    if (response?.body()?.code=="COMMON200") {
                        emailResult.value = BaseResponse.Success(response.body())

                    }

                }
                else if(response?.code()==400){
                    val errorbody=ErrorUtils.getErrorResponse(response.errorBody()!!)
                    Log.d("error body",errorbody.toString()!!)
                    emailResult.value = BaseResponse.Error(errorbody.message)

                }
            } catch (ex: Exception) {
            }
        }
    }

    val emailCheckResult: MutableLiveData<BaseResponse<EmailCheckResponse>> = MutableLiveData()
    fun emailCheckUser(emailAuthCode:String) {
        emailCheckResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val emailCheckRequest = EmailCheckRequest(
                    emailAuthId = emailAuthId.value?:0,
                    emailAuthCode = emailAuthCode
                )
                val response = userRepo.emailCheck(emailCheckRequest = emailCheckRequest)

                if (response?.body()?.code=="COMMON200") {
                    emailCheckResult.value = BaseResponse.Success(response.body())
                    _isEmailVerified.value=true
                } else {
                    emailCheckResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                emailCheckResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    val signupResult: MutableLiveData<BaseResponse<SignupResponse>> = MutableLiveData()

    fun signupUser() {
        signupResult.value = BaseResponse.Loading()
        val emptyKeywords= listOf<String>("null1","null2")

        viewModelScope.launch {
            // boss
            if(role.value==1){
                try {
                    val signupBossRequest = SignupBossRequest(
                        role=role.value?:1,
                        email = email.value.toString(),
                        password = pw.value.toString(),
                        rePassword = rePw.value.toString(),
                        name = name.value.toString(),
                        nickname=nickname.value?:"default",
                        gender = gender.value!!,
                        birthDate=birthDate.value?:"null",
//                        birthDate = LocalDate.parse(birthDate.value),
                        phone = phone.value.toString(),
                        emailAuthId = emailAuthId.value!!,//이메일인증식별자,
                        phoneAuthId = phoneAuthId.value!!, //전화번호인증식별자
                        profileImg=profileImg.value?:"null",
                        agreementUsage = agreementUsage.value!!,
                        agreementInfo=agreementInfo.value!!,
                        agreementAge=agreementAge.value!!,
                        agreementSms=agreementSms.value!!,
                        agreementEmail=agreementEmail.value!!,
                        agreementLocation=agreementLocation.value!!
                    )
                val response = userRepo.signupBoss(signupRequest = signupBossRequest)

                if (response?.body()?.code=="COMMON200") {
                    signupResult.value = BaseResponse.Success(response.body())
                } else {
                    signupResult.value = BaseResponse.Error(response?.message())
                }
                } catch (ex: Exception) {
                    signupResult.value = BaseResponse.Error(ex.message)
                }
            }
            // teacher
            else if(role.value==2){
                try{
                    val signupTeacherRequest = SignupTeacherRequest(
                        role=role.value?:1,
                        email = email.value.toString(),
                        password = pw.value.toString(),
                        rePassword = rePw.value.toString(),
                        name = name.value.toString(),
                        nickname=nickname.value?:"default",
                        gender = gender.value!!,
                        birthDate=birthDate.value?:"null",
//                        birthDate = LocalDate.parse(birthDate.value),
                        phone = phone.value.toString(),
                        emailAuthId = emailAuthId.value!!,
                        phoneAuthId = phoneAuthId.value!!,
                        profileImg=profileImg.value?:"null",
                        businessNumber=businessNum.value?:"null",
                        representative=representative.value?:"사장님",
                        openDate=openDate_str.value?: "null",
                        field=field.value?:"null",
                        career=_carrer_str.value!!.toInt(),
//                        career=career.value?:0,
                        introduction = introduction.value?:"",
                        keywords=keywords.value?:emptyKeywords,
                        agreementUsage = agreementUsage.value!!,
                        agreementInfo=agreementInfo.value!!,
                        agreementAge=agreementAge.value!!,
                        agreementSms=agreementSms.value!!,
                        agreementEmail=agreementEmail.value!!,
                        agreementLocation=agreementLocation.value!!
                    )
                    val response = userRepo.signupTeacher(signupRequest = signupTeacherRequest)

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
    }

    val phoneResult: MutableLiveData<BaseResponse<PhoneResponse>> = MutableLiveData()
    fun phoneUser(hash:String) {
        phoneResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val phoneRequest = PhoneRequest(
                    phone = phone.value.toString(),
                    purpose = 1,
                    appHash = hash
                )
                val response = userRepo.phoneUser(phoneRequest = phoneRequest)

                if (response?.body()?.code=="COMMON200") {
                    phoneResult.value = BaseResponse.Success(response.body())
                }
                else {
                    val errorbody=ErrorUtils.getErrorResponse(response?.errorBody()!!)
                    phoneResult.value = BaseResponse.Error(errorbody.message)
                }
            } catch (ex: Exception) {
                phoneResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    val phoneCheckResult: MutableLiveData<BaseResponse<PhoneCheckResponse>> = MutableLiveData()
    fun phoneCheckUser(phoneAuthCode: String) {
        phoneCheckResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val phoneCheckRequest = PhoneCheckRequest(
                    phoneAuthId = phoneAuthId.value?:0,
                    phoneAuthCode = phoneAuthCode
                )
                val response = userRepo.phoneCheck(phoneCheckRequest = phoneCheckRequest)

                if(response?.body()?.code=="COMMON200") {
                    phoneCheckResult.value = BaseResponse.Success(response.body())
                    _isPhoneVerified.value=true
                } else {
                    phoneCheckResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                phoneCheckResult.value = BaseResponse.Error(ex.message)
            }
        }
    }


    val nicknameResult: MutableLiveData<BaseResponse<NicknameResponse>> = MutableLiveData()
    fun nicknameUser() {
        nicknameResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val nicknameRequest = NicknameRequest(
                    nickname = nickname.value.toString()
                )
                val response = userRepo.nicknameUser(nicknameRequest = nicknameRequest)

                if (response?.body()?.result?.nicknameCheck==true) {
                    nicknameResult.value = BaseResponse.Success(response.body())
                }
                else {
                    val errorbody=ErrorUtils.getErrorResponse(response?.errorBody()!!)
                    nicknameResult.value = BaseResponse.Error(errorbody.message)
                }
            } catch (ex: Exception) {
                nicknameResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    var businessNumCheckResult:MutableLiveData<com.example.teacherforboss.util.base.BaseResponse<BusinessNumberCheckResponse>> =
        MutableLiveData()

    suspend fun businessNumCheck():Boolean{
        // TODO: 테스트 후 삭제
        _businessNum.value="380-20-01941"
//        _openDate.value= LocalDate.parse("2024-02-01")
        _openDateStr.value="2024-02-01"
        _representative.value="남윤수"

        Log.d("signup",representative.value.toString())

        return try {
            val businessNumCheckRequest = BusinessNumberCheckRequest(
                businessNumber = businessNum.value ?: "null",
                openDate = openDate_str.value ?: "",
                representative = representative.value ?: "null"
            )

            val response = userRepo.businessNumCheck(businessNumberCheckRequest = businessNumCheckRequest)

            if (response.code == "COMMON200") {
                _isBusinessVerified.value = response.result.checked
                businessNumCheckResult.value = response
                Log.d("signup", isBusinessVerified.value.toString())
                response.result.checked
            } else {
                false
            }
        } catch (ex: Exception) {
            Log.d("signup", ex.toString())
            false
        }

    }

    private val _presignedUrlListLiveData = MutableLiveData <presignedUrlListEntity> ()
    val presignedUrlLiveData : LiveData<presignedUrlListEntity> = _presignedUrlListLiveData

    suspend fun getPresignedUrlList(type:String,id:Long,imgCnt:Int){
        viewModelScope.launch {
            try{
                val presignedUrlListEntity= presignedUrlUseCase(
                    getPresingedUrlEntity(
                        type = type,
                        id=id,
                        imageCount = imgCnt
                    )
                )
                _presignedUrlListLiveData.value=presignedUrlListEntity
            }catch (ex:Exception){
                throw ex
            }
        }
    }

    fun setBossMode(){
        _role.value=1
    }
    fun setTeacherMode(){
        _role.value=2
    }

    fun changeToBossPageSize(){
        _totalPage.value= BOSS_FRAGMENT_SIZE
    }

    fun changeToTeacherPageSize(){
        _totalPage.value= TEACHER_FRAGMENT_SZIE
    }

    fun plusCurrentPage(){
        _currentPage.value=_currentPage.value!!+1f
    }
    fun minusCurrentPage(){
        _currentPage.value=_currentPage.value!!-1f
    }

    //timer
    private val _timerText=MutableLiveData<String>()
    val timerText:LiveData<String>
        get() = _timerText

    private val _timeOverState=MutableLiveData<Boolean>(false)
    val timeOverState:LiveData<Boolean>
        get() = _timeOverState

    fun startTimer(){
        timer.startTimer { timeLeft,state->
            _timerText.value=timeLeft
            if (state==true){
                _timeOverState.value=true
            }
        }
    }

    fun stopTimer(){
        timer.stopTimer()
    }

    companion object{
        private const val DEFAULT_PROGRESSBAR=1f
        const val BOSS_FRAGMENT_SIZE=8f // 보스: 온보딩 1 + 일반 4 + 프로필 1 =6
        const val TEACHER_FRAGMENT_SZIE=14f
    }

}