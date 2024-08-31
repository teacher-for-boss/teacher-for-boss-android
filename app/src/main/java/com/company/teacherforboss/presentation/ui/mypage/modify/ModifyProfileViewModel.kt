package com.company.teacherforboss.presentation.ui.mypage.modify

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.data.model.request.signup.NicknameRequest
import com.company.teacherforboss.data.model.response.BaseResponse
import com.company.teacherforboss.data.model.response.signup.NicknameResponse
import com.company.teacherforboss.data.repositoryImpl.UserRepositoryImpl
import com.company.teacherforboss.domain.model.aws.getPresingedUrlEntity
import com.company.teacherforboss.domain.model.mypage.ModifyBossProfileRequestEntity
import com.company.teacherforboss.domain.model.mypage.ModifyProfileResponseEntity
import com.company.teacherforboss.domain.model.mypage.ModifyTeacherProfileRequestEntity
import com.company.teacherforboss.domain.usecase.Member.ModifyBossProfileUseCase
import com.company.teacherforboss.domain.usecase.Member.ModifyTeacherProfileUseCase
import com.company.teacherforboss.domain.usecase.PresignedUrlUseCase
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_IMG_FILE_TYPE
import com.company.teacherforboss.util.base.ErrorUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyProfileViewModel @Inject constructor(
    private val modifyTeacherProfileUseCase: ModifyTeacherProfileUseCase,
    private val modifyBossProfileUseCase: ModifyBossProfileUseCase,
    private val presignedUrlUseCase:PresignedUrlUseCase
): ViewModel() {

    var _nickname= MutableLiveData<String>("")
    val nickname: LiveData<String>
        get()=_nickname

    var _nicknameCount= MutableLiveData<String>("0/10")
    val nicknameCount: LiveData<String>
        get()=_nicknameCount

    var _phone= MutableLiveData<String>("")
    val phone: LiveData<String>
        get()=_phone

    var _email= MutableLiveData<String>("")
    val email: LiveData<String>
        get()=_email

    // boss 변수들
    var _isUserImgSelected= MutableLiveData<Boolean>(false)
    val isUserImgSelected: LiveData<Boolean>
        get() = _isUserImgSelected

    var _profileImg= MutableLiveData<String>("https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png")
    val profileImg: LiveData<String>
        get() = _profileImg

    var _profileImgUri= MutableLiveData<Uri>(null)
    val profileImgUri: LiveData<Uri>
        get() = _profileImgUri

    val _profilePresignedUrl= MutableLiveData<String>()
    val profilePresignedUrl: LiveData<String> =_profilePresignedUrl

    var _fileType = MutableLiveData<String>("")
    val fileType: LiveData<String> get()=_fileType

    var _keywords=MutableLiveData<MutableList<String>>()
    val keywords:LiveData<MutableList<String>>
        get() = _keywords

    // 피봇 이후 회원가입 변수들
    var _field=MutableLiveData<String>("")
    val field:LiveData<String>
        get() = _field

    var _introduction=MutableLiveData<String>("")
    val introduction:LiveData<String>
        get() = _introduction

    var _enableNext = MutableLiveData<Boolean>(false)
    val enableNext:LiveData<Boolean>
        get() = _enableNext

    var _career_str=MutableLiveData<String>("")
    val career_str:LiveData<String>
        get() = _career_str

    var _phoneReveal=MutableLiveData<Boolean>(false)
    val phoneReveal:LiveData<Boolean>
        get() = _phoneReveal

    var _emailReveal=MutableLiveData<Boolean>(false)
    val emailReveal:LiveData<Boolean>
        get() = _emailReveal

    var _isInitializedView=MutableLiveData<Boolean>(false)
    val isInitializedView:LiveData<Boolean> get() = _isInitializedView

    // 사용자 초기 프로필 데이터
    val initialProfileImg = MutableLiveData<String>("")
    val initialNickname = MutableLiveData<String>("")
    val initialPhone = MutableLiveData<String>("")
    val initialEmail = MutableLiveData<String>("")
    val initialField = MutableLiveData<String>("")
    val initialCareer = MutableLiveData<String>("")
    val initialIntroduction = MutableLiveData<String>("")
    val initialKeywords = MutableLiveData<List<String>>()
    val initEmailOpen=MutableLiveData<Boolean>(false)
    val initPhoneOpen=MutableLiveData<Boolean>(false)

    val nicknameResult: MutableLiveData<BaseResponse<NicknameResponse>> = MutableLiveData()

    var _nicknameCheck = MutableLiveData<Boolean>(false)
    val nicknameCheck: LiveData<Boolean> get() = _nicknameCheck
    val userRepo= UserRepositoryImpl()

    private val _modifyTeacherProfileLiveData = MutableLiveData<ModifyProfileResponseEntity>()
    val modifyTeacherProfileLiveData: LiveData<ModifyProfileResponseEntity> get() = _modifyTeacherProfileLiveData

    private val _modifyBossProfileLiveData = MutableLiveData<ModifyProfileResponseEntity>()
    val modifyBossProfileLiveData: LiveData<ModifyProfileResponseEntity> get() = _modifyBossProfileLiveData

    init {
        nickname.observeForever {
            _nicknameCount.value = "${it.length}/10"
        }
    }
    fun setPhoneReveal(reveal: Boolean) {
        _phoneReveal.value = reveal
    }

    fun setEmailReveal(reveal: Boolean) {
        _emailReveal.value = reveal
    }
    fun setNicknameCheck(isChecked:Boolean){
        _nicknameCheck.value=isChecked
    }
    fun getNicknameCheck()=nicknameCheck.value

    fun setIsInitializedView(isInitializedState:Boolean){
        _isInitializedView.value=isInitializedState
    }
    fun getIsInitializedView()=isInitializedView.value?:false

    fun setIsUserImgSelected(bool:Boolean){_isUserImgSelected.value=bool}

    fun getIsUserImgSelected()=isUserImgSelected.value

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
                    val errorbody= ErrorUtils.getErrorResponse(response?.errorBody()!!)
                    nicknameResult.value = BaseResponse.Error(errorbody.message)
                }
            } catch (ex: Exception) { nicknameResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun modifyTeacherProfile() {
        var finalProfileImg=""
        if (isUserImgSelected.value==true){
            getFilteredPresingedUrl()?.let { finalProfileImg=it }
        }else finalProfileImg=profileImg.value!!

        viewModelScope.launch {
            try {
                val modifyTeacherProfileResponseEntity = modifyTeacherProfileUseCase(
                    ModifyTeacherProfileRequestEntity(
                        nickname = nickname.value!!,
                        phone = phone.value!!,
                        phoneOpen = phoneReveal.value!!,
                        email = email.value!!,
                        emailOpen = emailReveal.value!!,
                        field = field.value!!,
                        career = CareerToInt(),
                        introduction = introduction.value!!,
                        keywords = keywords.value!!,
                        profileImg = finalProfileImg
                    )
                )
                _modifyTeacherProfileLiveData.value = modifyTeacherProfileResponseEntity
            } catch (ex: Exception) {}
        }
    }

    fun modifyBossProfile() {
        var finalProfileImg=""
        if (isUserImgSelected.value==true){
            getFilteredPresingedUrl()?.let { finalProfileImg=it }
        }else finalProfileImg=profileImg.value!!

        viewModelScope.launch {
            try {
                val modifyBossProfileResponseEntity = modifyBossProfileUseCase(
                    ModifyBossProfileRequestEntity(
                        nickname = nickname.value!!,
                        profileImg = finalProfileImg
                    )
                )
                _modifyBossProfileLiveData.value = modifyBossProfileResponseEntity
            } catch (ex:Exception) {}
        }
    }

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

    fun getPresignedUrl()=profilePresignedUrl.value?:""

    fun getFilteredPresingedUrl()= profilePresignedUrl.value?.substringBefore(("?"))

    private fun validateFields() {
        _enableNext.value = !(_nickname.value.isNullOrEmpty() ||
                _field.value.isNullOrEmpty() ||
                _career_str.value.isNullOrEmpty() ||
                _introduction.value.isNullOrEmpty())
    }
    fun updateNicknameCount(nickname: String) {
        _nickname.value = nickname
    }
    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }
    fun setProfileImg(img: String) {
        _profileImg.value = img
    }
    fun setPhone(phone: String) {
        _phone.value = phone
    }
    fun setEmail(email: String) {
        _email.value = email
    }
    fun setField(field: String) {
        _field.value = field
    }
    fun setCareer(career: String) {
        _career_str.value = career
    }
    fun setIntroduction(introduction: String) {
        _introduction.value = introduction
    }
    fun setKeywords(keywordList: MutableList<String>) {
        _keywords.value = keywordList
    }
    fun CareerToInt(): Int {
        return career_str.value!!.toInt()
    }

    fun setEnableNextState(state:Boolean){
        _enableNext.value=state
    }
    fun getEnableNextState()=enableNext.value

    fun setFileType(fileType:String){
        if(fileType=="jpg") _fileType.value= DEFAULT_IMG_FILE_TYPE
        else _fileType.value="image/"+fileType
    }
    fun getFileType()=fileType.value?: DEFAULT_IMG_FILE_TYPE

    fun setUserImageUri(imgUri:Uri){
        _profileImgUri.value=imgUri
    }
    fun getUserImageUri() = profileImgUri.value?:null

    // set init
    fun setInitNickname(nickname: String) {
        initialNickname.value = nickname
    }
    fun setInitProfileImg(img: String) {
        initialProfileImg.value = img
    }
    fun setInitPhone(phone: String) {
        initialPhone.value = phone
    }
    fun setInitEmail(email: String) {
        initialEmail.value = email
    }
    fun setInitField(field: String) {
        initialField.value = field
    }
    fun setInitCareer(career: String) {
        initialCareer.value = career
    }
    fun setInitIntroduction(introduction: String) {
        initialIntroduction.value = introduction
    }
    fun setInitKeywords(keywordList: List<String>) {
        initialKeywords.value = keywordList
    }
    fun setInitEmailOpen(isOpened:Boolean){
        initEmailOpen.value=isOpened
    }
    fun setInitPhoneOpen(isOpened: Boolean){
        initPhoneOpen.value=isOpened
    }

    val isModified = MediatorLiveData<Boolean>().apply {
        value=false
        addSource(_profileImg) { checkIfModified() }
        addSource(_nickname) { checkIfModified() }
        addSource(_phone) { checkIfModified() }
        addSource(_email) { checkIfModified() }
        addSource(_field) { checkIfModified() }
        addSource(_career_str) { checkIfModified() }
        addSource(_introduction) { checkIfModified() }
        addSource(_keywords) { checkIfModified() }
        addSource(_phoneReveal){checkIfModified()}
        addSource(_emailReveal){checkIfModified()}
    }

    fun getIsModified()=isModified.value?:false

    fun checkIfModified() {
        val currentProfileImg = profileImg.value ?: ""
        val initialProfileImgValue = initialProfileImg.value ?: ""
        val currentNickname = nickname.value ?: ""
        val initialNicknameValue = initialNickname.value ?: ""
        val currentPhone = phone.value ?: ""
        val initialPhoneValue = initialPhone.value ?: ""
        val currentEmail = email.value ?: ""
        val initialEmailValue = initialEmail.value ?: ""
        val currentField = field.value ?: ""
        val initialFieldValue = initialField.value ?: ""
        val currentCareerStr = career_str.value ?: ""
        val initialCareerValue = initialCareer.value ?: ""
        val currentIntroduction = introduction.value ?: ""
        val initialIntroductionValue = initialIntroduction.value ?: ""
        val currentKeywords = keywords.value ?: mutableListOf()
        val initialKeywordsValue = initialKeywords.value ?: mutableListOf()
        val initEmailOpen=initEmailOpen.value?:false
        val currentEmailOpen=emailReveal.value
        val initPhoneOpen=initPhoneOpen.value?:false
        val currentPhoneOpen=phoneReveal.value

        // 키워드 리스트를 내용으로 비교
        val keywordsModified = currentKeywords.size != initialKeywordsValue.size ||
                currentKeywords.sorted() != initialKeywordsValue.sorted()

        val modified = currentProfileImg != initialProfileImgValue ||
                currentNickname != initialNicknameValue ||
                currentPhone != initialPhoneValue ||
                currentEmail != initialEmailValue ||
                currentField != initialFieldValue ||
                currentCareerStr != initialCareerValue ||
                currentIntroduction != initialIntroductionValue ||
                currentEmailOpen != initEmailOpen ||
                currentPhoneOpen != initPhoneOpen ||
                keywordsModified

        isModified.value = modified
    }

}