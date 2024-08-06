package com.company.teacherforboss.presentation.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.company.teacherforboss.domain.usecase.Member.ProfileUseCase
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
//    val mockTeacher = MyPageProfileEntity(
//        nickname = "하지은컨설팅",
//        profileImgUrl = "https://img-cdn.theqoo.net/bJgQuT.jpg",
//        role = "TEACHER",
//        teacherInfo = TeacherInfo(
//            level = "Lv.1 행운의 별",
//            leftAnswerCount = 5
//        )
//    )
//
//    val mockBoss = MyPageProfileEntity(
//        nickname = "장사를배워요",
//        profileImgUrl = "https://img-cdn.theqoo.net/bJgQuT.jpg",
//        role = "BOSS",
//        teacherInfo = null
//    )

    private val _userProfileInfoState = MutableStateFlow<UiState<MyPageProfileEntity>>(UiState.Empty)
    val userProfileInfoState get() = _userProfileInfoState.asStateFlow()

    val _role= MutableLiveData<String>("")
    val role: LiveData<String>
        get()=_role

    val _nickname = MutableLiveData<String>("")
    val nickname: LiveData<String> get() = _nickname

    val _profileImg = MutableLiveData<String>("")
    val profileImg: LiveData<String> get() = _profileImg

//    fun setMockProfileDate() {
//        _userProfileInfoState.value = UiState.Success(mockTeacher)
//        // _userProfileInfoState.value = UiState.Success(mockBoss)
//    }

    fun getUserProfile() {
        viewModelScope.launch {
            profileUseCase().onSuccess { mypageProfileEntity ->
                _userProfileInfoState.value=UiState.Success(mypageProfileEntity)
            }.onFailure { exception: Throwable ->
                _userProfileInfoState.value=UiState.Error(exception.message)
            }
        }
    }

    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun setProfileImg(img: String) {
        _profileImg.value = img
    }

}