package com.company.teacherforboss.presentation.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsRequestEntity
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

    var _bookmarkedQuestionList=MutableLiveData<List<BookmarkedQuestionsEntity>>()
    val bookmarkedQuestionList:LiveData<List<BookmarkedQuestionsEntity>> =_bookmarkedQuestionList

    val totalAnsweredQuestion= mutableListOf<List<BookmarkedQuestionsEntity>>()

    var _isInitializedView=MutableLiveData<Boolean>(false)
    val isInitializedView:LiveData<Boolean> get() = _isInitializedView

    var _lastQuestionId=MutableLiveData<Long>(0L)
    val lastQuestionId:LiveData<Long>
        get() = _lastQuestionId

    val size = MutableLiveData<Int>(10)

    val _hasNext=MutableLiveData<Boolean>().apply { value=true }
    val hasNext:LiveData<Boolean> get() = _hasNext

    private val _getSavedTeacherTalkQuestionsLiveData= MutableLiveData<BookmarkedQuestionsResponseEntity>()
    val getSavedTeacherTalkQuestionLiveData: LiveData<BookmarkedQuestionsResponseEntity>
        get() = _getSavedTeacherTalkQuestionsLiveData

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
                val bookmarkedQuestionsResponseEntity=bookmarkedQuestionsUseCase(
                    BookmarkedQuestionsRequestEntity(
                        lastQuestionId = lastQuestionId.value ?: 0L,
                        size = size.value?: 10,
                    )
                )
                setHasNext(bookmarkedQuestionsResponseEntity.hasNext)
                setBookmarkedTeacherTalkQuestionList(bookmarkedQuestionsResponseEntity.bookmarkedQuestionsList)
            } catch(ex:Exception){ }

        }
    }

    fun setHasNext(hasNext:Boolean){
        _hasNext.value=hasNext
    }
    fun setBookmarkedTeacherTalkQuestionList(bookmarkedQuestionsList:List<BookmarkedQuestionsEntity>){
        _bookmarkedQuestionList.value=bookmarkedQuestionsList
    }

    fun updateLastQuestionId(lastId: Long) {
        _lastQuestionId.value = lastId
    }

    fun getLastQuestionId(): Long? {
        return _lastQuestionId.value
    }


    fun clearData(){
        _bookmarkedQuestionList.value= emptyList()
//        totalAnsweredQuestion.clear()
//        _isInitializedView.value=false
        _lastQuestionId.value=0L
        _hasNext.value=false
    }


}