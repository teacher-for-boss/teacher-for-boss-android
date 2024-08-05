package com.company.teacherforboss.presentation.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.community.boss.PostEntity
import com.company.teacherforboss.domain.model.mypage.AnsweredQuestionEntity
import com.company.teacherforboss.domain.model.mypage.AnsweredQuestionResponseEntity
import com.company.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.company.teacherforboss.domain.usecase.Member.ProfileUseCase
import com.company.teacherforboss.domain.usecase.mypage.AnsweredQuestionUseCase
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val answeredQuestionUseCase: AnsweredQuestionUseCase
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

    private val _answeredQuestionState = MutableStateFlow<UiState<AnsweredQuestionResponseEntity>>(UiState.Empty)
    val answeredQuestionState get() = _answeredQuestionState.asStateFlow()

    private val _getAnsweredQuestionLiveData=MutableLiveData<AnsweredQuestionResponseEntity>()
    val getAnsweredQuestionLiveData:LiveData<AnsweredQuestionResponseEntity>
        get() = _getAnsweredQuestionLiveData

    var _answeredQuestion=MutableLiveData<List<AnsweredQuestionEntity>>()
    val answeredQuestion:LiveData<List<AnsweredQuestionEntity>> =_answeredQuestion

    val totalAnsweredQuestion= mutableListOf<List<AnsweredQuestionEntity>>()

    var _isInitializedView=MutableLiveData<Boolean>(false)
    val isInitializedView:LiveData<Boolean> get() = _isInitializedView

    var _lastPostId=MutableLiveData<Long>(0L)
    val lastPostId:LiveData<Long>
        get() = _lastPostId

    val _hasNext=MutableLiveData<Boolean>().apply { value=true }
    val hasNext:LiveData<Boolean> get() = _hasNext


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

    fun getAnsweredQuestion() {
        viewModelScope.launch {
            answeredQuestionUseCase().onSuccess { answeredQuestionResponseEntity ->
                    _answeredQuestionState.value = UiState.Success(answeredQuestionResponseEntity)
                    _answeredQuestion.value = answeredQuestionResponseEntity.answeredQuestionList
                }.onFailure { exception ->
                    _answeredQuestionState.value = UiState.Error(exception.message)
                }
        }
    }
    fun setHasNext(hasNext:Boolean){
        _hasNext.value=hasNext
    }
    fun setAnsweredQuestion(answeredQuestionList:List<AnsweredQuestionEntity>){
        _answeredQuestion.value=answeredQuestionList
    }

    fun clearData(){
        _answeredQuestion.value= emptyList()
        totalAnsweredQuestion.clear()
        _isInitializedView.value=false
        _lastPostId.value=0L
        _hasNext.value=false
    }

}