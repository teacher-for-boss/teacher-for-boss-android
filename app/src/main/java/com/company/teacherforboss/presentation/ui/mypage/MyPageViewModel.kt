package com.company.teacherforboss.presentation.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsResponseEntity
import com.company.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.company.teacherforboss.domain.usecase.Member.ProfileUseCase
import com.company.teacherforboss.domain.usecase.mypage.BookmarkedQuestionsUseCase
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val bookmarkedQuestionsUseCase: BookmarkedQuestionsUseCase
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

    private val _bookmarkedQuestionsState = MutableStateFlow<UiState<BookmarkedQuestionsResponseEntity>>(UiState.Empty)
    val bookmarkedQuestionsState get() = _bookmarkedQuestionsState.asStateFlow()

    private val _getBookmarkedQuestionsLiveData=MutableLiveData<BookmarkedQuestionsResponseEntity>()
    val getBookmarkedQuestionsLiveData:LiveData<BookmarkedQuestionsResponseEntity>
        get() = _getBookmarkedQuestionsLiveData

    var _bookmarkedQuestion=MutableLiveData<List<BookmarkedQuestionsEntity>>()
    val bookmarkedQuestion:LiveData<List<BookmarkedQuestionsEntity>> =_bookmarkedQuestion

    val totalAnsweredQuestion= mutableListOf<List<BookmarkedQuestionsEntity>>()

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

    fun getBookmarkedQuestions() {
        viewModelScope.launch {
            try {
                val bookmarkedQuestionsResponseEntity = bookmarkedQuestionsUseCase()
                _bookmarkedQuestionsState.value = UiState.Success(bookmarkedQuestionsResponseEntity)
            } catch (ex:Exception) { _bookmarkedQuestionsState.value = UiState.Error(ex.message) }
        }
    }

    fun setHasNext(hasNext:Boolean){
        _hasNext.value=hasNext
    }
    fun setAnsweredQuestion(bookmarkedQuestionsList:List<BookmarkedQuestionsEntity>){
        _bookmarkedQuestion.value=bookmarkedQuestionsList
    }

    fun clearData(){
        _bookmarkedQuestion.value= emptyList()
        totalAnsweredQuestion.clear()
        _isInitializedView.value=false
        _lastPostId.value=0L
        _hasNext.value=false
    }

}