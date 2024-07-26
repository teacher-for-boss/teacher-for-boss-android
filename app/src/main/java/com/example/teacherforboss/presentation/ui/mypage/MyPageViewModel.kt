package com.example.teacherforboss.presentation.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.example.teacherforboss.domain.model.mypage.MyPageProfileEntity.TeacherInfo
import com.example.teacherforboss.domain.usecase.Member.ProfileUseCase
import com.example.teacherforboss.util.view.UiState
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

    private val _myPageProfileLiveData = MutableLiveData<MyPageProfileEntity>()
    val myPageProfileLiveData: LiveData<MyPageProfileEntity> get() = _myPageProfileLiveData

    private val _userProfileInfoState = MutableStateFlow<UiState<MyPageProfileEntity>>(UiState.Empty)
    val userProfileInfoState get() = _userProfileInfoState.asStateFlow()

//    fun setMockProfileDate() {
//        _userProfileInfoState.value = UiState.Success(mockTeacher)
//        // _userProfileInfoState.value = UiState.Success(mockBoss)
//    }
//
//    fun getUserProfile() {
//        viewModelScope.launch {
//            // TODO UseCase 바탕으로 value에 UiState.Success 값 넣기
//        }
//    }

    fun getProfile() {
        viewModelScope.launch {
            try {
                val myPageProfileEntity = profileUseCase()
                _myPageProfileLiveData.value = myPageProfileEntity
            } catch (ex:Exception) {}
        }
    }
}