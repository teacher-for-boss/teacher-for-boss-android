package com.company.teacherforboss.presentation.ui.auth.signup

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.data.model.request.signup.BusinessNumberCheckRequest
import com.company.teacherforboss.data.model.response.BaseResponse
import com.company.teacherforboss.data.repositoryImpl.UserRepositoryImpl
import com.company.teacherforboss.data.model.request.signup.EmailCheckRequest
import com.company.teacherforboss.data.model.response.signup.EmailCheckResponse
import com.company.teacherforboss.data.model.request.signup.EmailRequest
import com.company.teacherforboss.data.model.request.signup.NicknameRequest
import com.company.teacherforboss.data.model.response.signup.EmailResponse
import com.company.teacherforboss.data.model.response.signup.SignupResponse
import com.company.teacherforboss.data.model.request.signup.PhoneCheckRequest
import com.company.teacherforboss.data.model.response.signup.PhoneCheckResponse
import com.company.teacherforboss.data.model.request.signup.PhoneRequest
import com.company.teacherforboss.data.model.response.signup.NicknameResponse
import com.company.teacherforboss.data.model.request.signup.SignupBossRequest
import com.company.teacherforboss.data.model.request.signup.SignupTeacherRequest
import com.company.teacherforboss.data.model.request.signup.SocialSignupBossRequest
import com.company.teacherforboss.data.model.request.signup.SocialSignupTeacherRequest
import com.company.teacherforboss.data.model.response.login.socialLoginResponse
import com.company.teacherforboss.data.model.response.signup.BusinessNumberCheckResponse
import com.company.teacherforboss.data.model.response.signup.PhoneResponse
import com.company.teacherforboss.domain.model.aws.getPresingedUrlEntity
import com.company.teacherforboss.domain.model.aws.presignedUrlListEntity
import com.company.teacherforboss.domain.usecase.PresignedUrlUseCase
import com.company.teacherforboss.util.Timer.Custom3mTimer
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_BOSS_PROFILE_IMG_URL
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_IMG_FILE_TYPE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_PROFILE_IMG_URL
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_TEACHER_PROFILE_IMG_URL
import com.company.teacherforboss.util.base.ErrorUtils
import com.company.teacherforboss.util.base.parseErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    val timer: Custom3mTimer,
    private val presignedUrlUseCase: PresignedUrlUseCase
//    private val userRepo: UserRepository

): ViewModel() {
    var _socialType = MutableLiveData<Int>(0)
    val socialType:LiveData<Int>
        get() = _socialType

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

    var _nicknameCount= MutableLiveData<String>("0/10")
    val nicknameCount: LiveData<String>
        get()=_nicknameCount

    var _fileType = MutableLiveData<String>("")
    val fileType: LiveData<String> get()=_fileType

    // boss 변수들
    var _isUserImgSelected=MutableLiveData<Boolean>(false)
    val isUserImgSelected:LiveData<Boolean>
        get() = _isUserImgSelected

    var _profileImg=MutableLiveData<String>(DEFAULT_PROFILE_IMG_URL)
    val profileImg: LiveData<String>
        get() = _profileImg

    var _profileImgUri=MutableLiveData<Uri>(null)
    val profileImgUri: LiveData<Uri>
        get() = _profileImgUri

    val _profilePresignedUrl=MutableLiveData<String>()
    val profilePresignedUrl:LiveData<String> =_profilePresignedUrl

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

    var enableNext = MutableLiveData<Boolean>(false)

    // activity page 관련
    var _currentPage=MutableLiveData<Float>(DEFAULT_PROGRESSBAR)
    val currentPage:LiveData<Float>
        get() = _currentPage

    var _totalPage=MutableLiveData<Float>(TEACHER_FRAGMENT_SZIE)
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

    var _birthDate=MutableLiveData<String?>("")
    val birthDate:LiveData<String?>
        get() = _birthDate

    var emailAuthId=MutableLiveData<Long>(0) //test용 원래는 0으로 설정
    var phoneAuthId=MutableLiveData<Long>(0)

    val num_check= MutableLiveData<Boolean>(false)
    val eng_check= MutableLiveData<Boolean>(false)
    val special_check= MutableLiveData<Boolean>(false)
    val length_check= MutableLiveData<Boolean>(false)
    val rePw_check= MutableLiveData<Boolean>(false)
    val all_check= MutableLiveData<Boolean>(false)
    val nickname_check= MutableLiveData<Boolean>(false)

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
    var _isPhoneVerified_str=MutableLiveData<String>("T") // api 테스트 완료 추후 false로 수정
    val isPhoneVerified_str:LiveData<String>
        get()=_isPhoneVerified_str

    //휴대폰 인증 여부 boolean->data binding
    var _isPhoneVerified=MutableLiveData<Boolean>(false) //TODO
    val isPhoneVerified:LiveData<Boolean>
        get()=_isPhoneVerified

    init{
        role.observeForever {
            when(role.value){
                1->_profileImg.value= DEFAULT_BOSS_PROFILE_IMG_URL
                2->_profileImg.value= DEFAULT_TEACHER_PROFILE_IMG_URL
            }
        }
    }
    // image
    fun setUserImageUri(imgUri:Uri){
        _profileImgUri.value=imgUri
    }
    fun getUserImageUri() = profileImgUri.value?:null

    fun setIsUserImgSelected(bool:Boolean){_isUserImgSelected.value=bool}

    fun getIsUserImgSelected()=isUserImgSelected.value

    fun getPresignedUrl()=profilePresignedUrl.value?:""

    fun getFilteredPresingedUrl()= profilePresignedUrl.value?.substringBefore(("?"))

    var _isInitializedDialog=MutableLiveData<Boolean>(false)
    val isInitializedDialog:LiveData<Boolean> get() = _isInitializedDialog

    fun setIsInitializedDialog(isInitializedState:Boolean){
        _isInitializedDialog.value=isInitializedState
    }
    fun getIsInitializedDialog()=isInitializedDialog.value?:false

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
        val pattern= Pattern.compile("010\\d{3,4}\\d{4}")
        phone_check.value=pattern.matcher(livePhone.value.toString()).matches()

    }

    // 사업자 번호 체크
    fun bn_validation(){
        val pattern=Pattern.compile("^\\d{3}-\\d{2}-\\d{5}$")
        _businessNumCheck.value=pattern.matcher(businessNum.value.toString()).matches()
        Log.d("bn",businessNumCheck.value.toString())
    }
    init {
        nickname.observeForever {
            _nicknameCount.value = "${it.length}/10"
        }
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
                else{
                    val errorMessage = response?.let { parseErrorResponse(it) }
                    errorMessage?.let { emailResult.value = BaseResponse.Error(errorMessage) }
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
                    val errorMessage = response?.let { parseErrorResponse(it) }
                    errorMessage?.let { emailCheckResult.value = BaseResponse.Error(errorMessage) }
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

        var finalProfileImg=""
        if (isUserImgSelected.value==true){
            getFilteredPresingedUrl()?.let { finalProfileImg=it }
        }else finalProfileImg=profileImg.value!!

        var formatted_openDate=""
        if(openDate_str.value!=null && openDate_str.value!="YYYY-MM-DD"){
            val formatter = DateTimeFormatter.ofPattern("yyyy-M-d")
            formatted_openDate = LocalDate.parse(openDate_str.value, formatter).toString()
            Log.d("test",formatted_openDate.toString())
        }

        var formatted_BirthDate=""
        if(birthDate.value!=null && birthDate.value!=""){
            formatted_BirthDate = formatBirthDate(birthDate.value.toString())
        }

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
                        birthDate=formatted_BirthDate?:null,
                        phone = phone.value.toString(),
                        emailAuthId = emailAuthId.value!!,//이메일인증식별자,
                        phoneAuthId = phoneAuthId.value!!, //전화번호인증식별자
//                        profileImg=profileImg.value?:"null",
                        profileImg=finalProfileImg,
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
                        val errorMessage = response?.let { parseErrorResponse(it) }
                        errorMessage?.let { signupResult.value = BaseResponse.Error(errorMessage) }
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
                        birthDate=formatted_BirthDate?:null,
                        phone = phone.value.toString(),
                        emailAuthId = emailAuthId.value!!,
                        phoneAuthId = phoneAuthId.value!!,
                        profileImg=profileImg.value?:"null",
                        businessNumber=businessNum.value?:"null",
                        representative=representative.value?:"사장님",
                        openDate=formatted_openDate?: "null",
                        field=field.value?:"null",
                        career=_carrer_str.value!!.toInt(),
                        introduction = introduction.value?:"",
                        keywords=keywords.value?:emptyKeywords,
                        bank=bank.value?:"null",
                        accountNumber=accountNum.value?:"",
                        accountHolder=accountHoler.value?:"",
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
                        val errorMessage = response?.let { parseErrorResponse(it) }
                        errorMessage?.let { signupResult.value = BaseResponse.Error(errorMessage) }
                    }

                } catch (ex: Exception) {
                    signupResult.value = BaseResponse.Error(ex.message)
                }
            }
        }
    }

    val socialSignupResult: MutableLiveData<BaseResponse<socialLoginResponse>> = MutableLiveData()

    fun socialSignup(type:String){
        socialSignupResult.value= BaseResponse.Loading()
        val emptyKeywords= listOf<String>("null1","null2")

        var type_num=0
        if(type=="KAKAO") type_num=2
        else type_num=3

        var formatted_openDate=""
        if(openDate_str.value!=null && openDate_str.value!="YYYY-MM-DD"){
            val formatter = DateTimeFormatter.ofPattern("yyyy-M-d")
            formatted_openDate = LocalDate.parse(openDate_str.value, formatter).toString()
            Log.d("test",formatted_openDate.toString())
        }

        var formatted_BirthDate=""
        if(birthDate.value!=null && birthDate.value!=""){
            formatted_BirthDate = formatBirthDate(birthDate.value.toString())
        }

        viewModelScope.launch {
            // boss
            if(role.value==1){
                try {
                    val signupBossRequest = SocialSignupBossRequest(
                        role=role.value?:1,
                        email = email.value.toString(),
                        name = name.value.toString(),
                        nickname=nickname.value?:"default",
                        gender = gender.value!!,
                        birthDate=formatted_BirthDate?:null,
//                        birthDate = LocalDate.parse(birthDate.value),
                        phone = phone.value.toString(),
                        profileImg=profileImg.value?:null,

                    )
                    val response = userRepo.socialBossSignup(socialType=type_num,signupRequest = signupBossRequest)

                    if (response?.body()?.code=="COMMON200") {
                        socialSignupResult.value = BaseResponse.Success(response.body())
                    }
                    else {
                        val errorMessage = response?.let { parseErrorResponse(it) }
                        errorMessage?.let { socialSignupResult.value = BaseResponse.Error(errorMessage ?: "Unknown error") }
                    }
                } catch (ex: Exception) {
                    socialSignupResult.value = BaseResponse.Error(ex.message)
                }
            }
            // teacher
            else if(role.value==2){
                try{
                    val signupTeacherRequest = SocialSignupTeacherRequest(
                        role=role.value?:1,
                        email = email.value.toString(),
                        name = name.value.toString(),
                        nickname=nickname.value?:"default",
                        gender = gender.value!!,
                        birthDate=formatted_BirthDate?:null,
//                        birthDate = LocalDate.parse(birthDate.value),
                        phone = phone.value.toString(),
                        profileImg=profileImg.value?:"null",
                        businessNumber=businessNum.value?:"null",
                        representative=representative.value?:"사장님",
                        openDate=formatted_openDate.toString()?: "null",
                        field=field.value?:"null",
                        career=_carrer_str.value!!.toInt(),
//                        career=career.value?:0,
                        introduction = introduction.value?:"",
                        keywords=keywords.value?:emptyKeywords,
                        bank=bank.value?:"null",
                        accountNumber=accountNum.value?:"",
                        accountHolder=accountHoler.value?:"",
                    )
                    val response = userRepo.socialTeacherSignup(socialType=type_num,signupRequest = signupTeacherRequest)

                    if (response?.body()?.code=="COMMON200") {
                        socialSignupResult.value = BaseResponse.Success(response.body())
                    } else {
                        val errorMessage = response?.let { parseErrorResponse(it) }
                        errorMessage?.let { socialSignupResult.value = BaseResponse.Error(errorMessage) }
                    }

                } catch (ex: Exception) {
                    socialSignupResult.value = BaseResponse.Error(ex.message)
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
                    val errorMessage = response?.let { parseErrorResponse(it) }
                    errorMessage?.let { phoneResult.value = BaseResponse.Error(errorMessage) }
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
    var nicknameCheck = MutableLiveData<Boolean>(false)
    var nicknamePrevCheck=MutableLiveData<Boolean>(null)
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
                else if (response?.body()?.result?.nicknameCheck==false) {
                    nicknameResult.value = BaseResponse.Error("닉네임 사용 불가")
                }
                else {
                    val errorMessage = response?.let { parseErrorResponse(it) }
                    errorMessage?.let { nicknameResult.value = BaseResponse.Error(errorMessage) }
                }
            } catch (ex: Exception) { nicknameResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    var businessNumCheckResult:MutableLiveData<com.company.teacherforboss.util.base.BaseResponse<BusinessNumberCheckResponse>> =
        MutableLiveData()

    suspend fun businessNumCheck():Boolean{

        val dateString = openDate_str.value
        val formatter = DateTimeFormatter.ofPattern("yyyy-M-d")
        val formatted_openDate = LocalDate.parse(dateString, formatter)
        return try {
            val businessNumCheckRequest = BusinessNumberCheckRequest(
                businessNumber = businessNum.value ?: "null",
                openDate = formatted_openDate.toString()?: "",
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

    fun getPresignedUrlList(){
        viewModelScope.launch {
            try{
                val presignedUrlListEntity= presignedUrlUseCase(
                    getPresingedUrlEntity(
                        uuid = null,
                        lastIndex=0,
                        imageCount = 1,
                        origin="profiles"
                    )
                )
                _profilePresignedUrl.value=presignedUrlListEntity.presignedUrlList[0]
            }catch (ex:Exception){
                throw ex
            }
        }
    }
    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun nickname_not_blank_validation(){
        nicknamePrevCheck.value=!_nickname.value.isNullOrEmpty()
    }

    fun nickname_pattern_validation() {
        val nicknamePattern = Regex("[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]+")
        nicknamePrevCheck.value = !_nickname.value.isNullOrEmpty() && !nicknamePattern.containsMatchIn(_nickname.value!!)
    }

    fun formatBirthDate(birthDate: String?): String {
        if (!birthDate.isNullOrEmpty()) {
            val dateParts = birthDate.replace(Regex("\\+"), "-").split("-")

            if (dateParts.size == 3) {
                val year = dateParts[0]
                val month = dateParts[1]
                val day = dateParts[2].padStart(2, '0')

                return "$year-$month-$day"
            }
        }
        return ""
    }

    fun checkFilled() {
        if (nicknamePrevCheck.value == true &&
            !field.value.isNullOrEmpty() &&
            !introduction.value.isNullOrEmpty()
        ) {
            enableNext.value = true
        } else {
            enableNext.value = false
        }
    }
    fun validateNickname(nickname: String) {
        setNickname(nickname)
        nickname_not_blank_validation()
        nickname_pattern_validation()
        checkFilled()
    }

    fun setBossMode(){
        _role.value=1
        _profileImg.value= DEFAULT_BOSS_PROFILE_IMG_URL
    }
    fun setTeacherMode(){
        _role.value=2
        _profileImg.value= DEFAULT_TEACHER_PROFILE_IMG_URL
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

    fun setFileType(fileType:String){
        if(fileType=="jpg") _fileType.value= DEFAULT_IMG_FILE_TYPE
        else _fileType.value="image/"+fileType
    }
    fun getFileType()=fileType.value?:DEFAULT_IMG_FILE_TYPE


    fun setProfileUserImg(){
        _profileImg.value=profilePresignedUrl.value?.substringBefore("?")
    }

    //timer
    private val _timerText=MutableLiveData<String>()
    val timerText:LiveData<String>
        get() = _timerText

    private val _timeOverState=MutableLiveData<Boolean>(false)
    val timeOverState:LiveData<Boolean>
        get() = _timeOverState

    fun startTimer(){
        _timeOverState.value = false
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