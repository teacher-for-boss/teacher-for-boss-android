package com.company.teacherforboss.presentation.ui.mypage.modify

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.data.model.request.signup.NicknameRequest
import com.company.teacherforboss.data.model.response.BaseResponse
import com.company.teacherforboss.data.model.response.signup.NicknameResponse
import com.company.teacherforboss.data.repository.UserRepositoryImpl
import com.company.teacherforboss.domain.model.mypage.ModifyBossProfileRequestEntity
import com.company.teacherforboss.domain.model.mypage.ModifyProfileResponseEntity
import com.company.teacherforboss.domain.model.mypage.ModifyTeacherProfileRequestEntity
import com.company.teacherforboss.domain.usecase.Member.ModifyBossProfileUseCase
import com.company.teacherforboss.domain.usecase.Member.ModifyTeacherProfileUseCase
import com.company.teacherforboss.util.base.ErrorUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyProfileViewModel @Inject constructor(
    private val modifyTeacherProfileUseCase: ModifyTeacherProfileUseCase,
    private val modifyBossProfileUseCase: ModifyBossProfileUseCase
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
    var _isDefaultImgSelected= MutableLiveData<Boolean>(false)
    val isDefaultImgSelected: LiveData<Boolean>
        get() = _isDefaultImgSelected


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

    var enableNext = MutableLiveData<Boolean>(false)

    var _carrer_str=MutableLiveData<String>("")
    val career_str:LiveData<String>
        get() = _carrer_str

    var _phoneReveal=MutableLiveData<Boolean>(false)
    val phoneReveal:LiveData<Boolean>
        get() = _phoneReveal

    var _emailReveal=MutableLiveData<Boolean>(false)
    val emailReveal:LiveData<Boolean>
        get() = _emailReveal

    val nicknameResult: MutableLiveData<BaseResponse<NicknameResponse>> = MutableLiveData()
    var nicknameCheck = MutableLiveData<Boolean>(false)
    val userRepo= UserRepositoryImpl()

    private val _modifyTeacherProfileLiveData = MutableLiveData<ModifyProfileResponseEntity>()
    val modifyTeacherProfileLiveData: LiveData<ModifyProfileResponseEntity> get() = _modifyTeacherProfileLiveData

    private val _modifyBossProfileLiveData = MutableLiveData<ModifyProfileResponseEntity>()
    val modifyBossProfileLiveData: LiveData<ModifyProfileResponseEntity> get() = _modifyBossProfileLiveData

    init {
        _nickname.observeForever {
            _nicknameCount.value = "${it.length}/10"
            validateFields()
        }
        _phone.observeForever {
            validateFields()
        }
        _email.observeForever {
            validateFields()
        }
        _field.observeForever {
            validateFields()
        }
        _carrer_str.observeForever {
            validateFields()
        }
        _introduction.observeForever {
            validateFields()
        }
    }
    fun setPhoneReveal(reveal: Boolean) {
        _phoneReveal.value = reveal
    }

    fun setEmailReveal(reveal: Boolean) {
        _emailReveal.value = reveal
    }


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
                        profileImg = profileImg.value!!
                    )
                )
                _modifyTeacherProfileLiveData.value = modifyTeacherProfileResponseEntity
            } catch (ex: Exception) {}
        }
    }

    fun modifyBossProfile() {
        viewModelScope.launch {
            try {
                val modifyBossProfileResponseEntity = modifyBossProfileUseCase(
                    ModifyBossProfileRequestEntity(
                        nickname = nickname.value!!,
                        profileImg = profileImg.value!!
                    )
                )
                _modifyBossProfileLiveData.value = modifyBossProfileResponseEntity
            } catch (ex:Exception) {}
        }
    }

    private fun validateFields() {
        enableNext.value = !(_nickname.value.isNullOrEmpty() ||
                _field.value.isNullOrEmpty() ||
                _carrer_str.value.isNullOrEmpty() ||
                _introduction.value.isNullOrEmpty())
    }
    fun updateNicknameCount(nickname: String) {
        _nickname.value = nickname
    }
    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }
    fun setPhone(phone: String) {
        _phone.value = phone
    }
    fun setKeywords(keywordList: MutableList<String>) {
        _keywords.value = keywordList
    }
    fun CareerToInt(): Int {
        return career_str.value!!.toInt()
    }

}