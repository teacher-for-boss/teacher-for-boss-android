package com.company.teacherforboss.presentation.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsRequestEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsResponseEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsRequestEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsResponseEntity
import com.company.teacherforboss.domain.model.mypage.ChipInfoResponseEntity
import com.company.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.company.teacherforboss.domain.usecase.Member.ProfileUseCase
import com.company.teacherforboss.domain.usecase.mypage.BookmarkedPostsUseCase
import com.company.teacherforboss.domain.usecase.mypage.BookmarkedQuestionsUseCase
import com.company.teacherforboss.domain.usecase.mypage.ChipInfoUseCase
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_PROFILE_ID
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val bookmarkedQuestionsUseCase: BookmarkedQuestionsUseCase,
    private val chipInfoUseCase: ChipInfoUseCase,
    private val bookmarkedPostsUseCase: BookmarkedPostsUseCase
) : ViewModel() {
    @Inject lateinit var localDataSource:LocalDataSource
    private val _userProfileInfoState = MutableStateFlow<UiState<MyPageProfileEntity>>(UiState.Empty)
    val userProfileInfoState get() = _userProfileInfoState.asStateFlow()

    private val _userChipInfoState = MutableStateFlow<UiState<ChipInfoResponseEntity>>(UiState.Empty)
    val userChipInfoState get() = _userChipInfoState.asStateFlow()

    val _role = MutableLiveData<String>("")
    val role: LiveData<String>
        get() = _role

    val _nickname = MutableLiveData<String>("")
    val nickname: LiveData<String> get() = _nickname

    val _profileImg = MutableLiveData<String>("")
    val profileImg: LiveData<String> get() = _profileImg

    val _memberId=MutableLiveData<Long>(-1L)
    val memberId: LiveData<Long> get()=_memberId

    private val _bookmarkedQuestionsState = MutableStateFlow<UiState<BookmarkedQuestionsResponseEntity>>(UiState.Empty)
    val bookmarkedQuestionsState get() = _bookmarkedQuestionsState.asStateFlow()

    private val _bookmarkedPostsState =
        MutableStateFlow<UiState<BookmarkedPostsResponseEntity>>(UiState.Empty)
    val bookmarkedPostsState get() = _bookmarkedPostsState.asStateFlow()

    private val _getBookmarkedQuestionsLiveData =
        MutableLiveData<BookmarkedQuestionsResponseEntity>()
    val getBookmarkedQuestionsLiveData: LiveData<BookmarkedQuestionsResponseEntity>
        get() = _getBookmarkedQuestionsLiveData

    private val _getBookmarkedPostsLiveData = MutableLiveData<BookmarkedPostsResponseEntity>()
    val getBookmarkedPostsLiveData: LiveData<BookmarkedPostsResponseEntity>
        get() = _getBookmarkedPostsLiveData

    var _bookmarkedQuestionList = MutableLiveData<List<BookmarkedQuestionsEntity>>()
    val bookmarkedQuestionList: LiveData<List<BookmarkedQuestionsEntity>> = _bookmarkedQuestionList

    var _bookmarkedPostList = MutableLiveData<List<BookmarkedPostsEntity>>()
    val bookmarkedPostList: LiveData<List<BookmarkedPostsEntity>> = _bookmarkedPostList

    val totalAnsweredQuestion = mutableListOf<List<BookmarkedQuestionsEntity>>()

    var _isInitializedView = MutableLiveData<Boolean>(false)
    val isInitializedView: LiveData<Boolean> get() = _isInitializedView

    var _lastQuestionId = MutableLiveData<Long>(0L)
    val lastQuestionId: LiveData<Long>
        get() = _lastQuestionId

    var _lastPostId = MutableLiveData<Long>(0L)
    val lastPostId: LiveData<Long>
        get() = _lastPostId

    val bookmarkedQuestionSize = MutableLiveData<Int>(10)
    val bookmarkedPostSize = MutableLiveData<Int>(10)

    val _hasNextQuestion = MutableLiveData<Boolean>().apply { value = true }
    val hasNextQuestion: LiveData<Boolean> get() = _hasNextQuestion

    private var isQuestionLoading = false

    val _hasNextPost = MutableLiveData<Boolean>().apply { value = true }
    val hasNextPost: LiveData<Boolean> get() = _hasNextPost

    private var isPostLoading = false

    private val _getSavedTeacherTalkQuestionsLiveData =
        MutableLiveData<BookmarkedQuestionsResponseEntity>()
    val getSavedTeacherTalkQuestionLiveData: LiveData<BookmarkedQuestionsResponseEntity>
        get() = _getSavedTeacherTalkQuestionsLiveData

    private val _getSavedPostsLiveData = MutableLiveData<BookmarkedPostsResponseEntity>()
    val getSavedPostsLiveData: LiveData<BookmarkedPostsResponseEntity>
        get() = _getSavedPostsLiveData

    var _memeberRole = MutableLiveData<String>()
    val memberRole:LiveData<String> get() = _memeberRole


    var _answerCount = MutableLiveData<Long>()
    val answerCount:LiveData<Long> get() = _answerCount

    var _questionCount = MutableLiveData<Long>()
    val questionCount:LiveData<Long> get() = _questionCount

    var _bookmarkCount = MutableLiveData<Long>()
    val bookmarkCount:LiveData<Long> get() = _bookmarkCount

    var _points = MutableLiveData<Int>()
    val points:LiveData<Int> get() = _points

    var _questionTicketCount = MutableLiveData<Int>()
    val questionTicketCount:LiveData<Int> get() = _questionTicketCount

//    fun setMockProfileDate() {
//        _userProfileInfoState.value = UiState.Success(mockTeacher)
//        // _userProfileInfoState.value = UiState.Success(mockBoss)
//    }

    fun getUserProfile() {
        viewModelScope.launch {
            profileUseCase().onSuccess { mypageProfileEntity ->
                _userProfileInfoState.value = UiState.Success(mypageProfileEntity)
            }.onFailure { exception: Throwable ->
                _userProfileInfoState.value = UiState.Error(exception.message)
            }
        }
    }

    fun getUserChipInfo() {
        viewModelScope.launch {
            chipInfoUseCase().onSuccess {chipInfoUseCase ->
                _userChipInfoState.value = UiState.Success(chipInfoUseCase)
            }.onFailure { exception: Throwable ->
                _userChipInfoState.value = UiState.Error(exception.message)
            }
        }
    }

    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun setProfileImg(img: String) {
        _profileImg.value = img
    }

    fun getBookmarkedQuestions() {
        if (isQuestionLoading) return

        isQuestionLoading = true
        viewModelScope.launch {
            try {
                val bookmarkedQuestionsResponseEntity = bookmarkedQuestionsUseCase(
                    BookmarkedQuestionsRequestEntity(
                        lastQuestionId = lastQuestionId.value ?: 0L,
                        size = bookmarkedQuestionSize.value ?: 10,
                    )
                )
                _bookmarkedQuestionsState.value =
                    UiState.Success(bookmarkedQuestionsResponseEntity)
                setHasNextQuestion(bookmarkedQuestionsResponseEntity.hasNext)
                setBookmarkedQuestionList(bookmarkedQuestionsResponseEntity.bookmarkedQuestionsList)
            } catch (ex: Exception) {
                _bookmarkedQuestionsState.value = UiState.Error(ex.message)
            } finally {
                isQuestionLoading = false
            }
        }
    }

    fun getBookmarkedPosts() {
        viewModelScope.launch {
            try {
                val bookmarkedPostsResponseEntity = bookmarkedPostsUseCase(
                    BookmarkedPostsRequestEntity(
                        lastPostId = lastPostId.value ?: 0L,
                        size = bookmarkedQuestionSize.value ?: 10,
                    )
                )
                setHasNextPost(bookmarkedPostsResponseEntity.hasNext)
                setBookmarkedPostsList(bookmarkedPostsResponseEntity.postList)
            } catch (ex: Exception) {
            }
        }
    }

    fun setHasNextQuestion(hasNext: Boolean) {
        _hasNextQuestion.value = hasNext
    }

    fun setHasNextPost(hasNext: Boolean) {
        _hasNextPost.value = hasNext
    }

    fun setBookmarkedQuestionList(bookmarkedQuestionsList: List<BookmarkedQuestionsEntity>) {
        _bookmarkedQuestionList.value = bookmarkedQuestionsList
    }

    fun setBookmarkedPostsList(postList: List<BookmarkedPostsEntity>) {
        _bookmarkedPostList.value = postList
    }

    fun setLastQuestionId(lastId: Long) {
        _lastQuestionId.value = lastId
    }

    fun getLastQuestionId(): Long? {
        return _lastQuestionId.value
    }

    fun setLastPostId(lastId: Long) {
        _lastPostId.value = lastId
    }

    fun getLastPostId(): Long? {
        return _lastPostId.value
    }


    fun clearQuestionData() {
        _bookmarkedQuestionList.value = emptyList()
//        totalAnsweredQuestion.clear()
//        _isInitializedView.value=false
        _lastQuestionId.value = 0L
        _hasNextQuestion.value = false
    }

    fun clearPostData() {
        _bookmarkedQuestionList.value = emptyList()
        _lastQuestionId.value = 0L
        _hasNextQuestion.value = false

        fun setRole(role: String) {
            _role.value = role
        }

        fun getRole() = role.value

        fun setMemberId(memberId: Long) {
            _memberId.value = memberId
            localDataSource.saveUserInfo(TEACHER_PROFILE_ID, memberId.toString())
        }

        fun getMemberId() = memberId.value

        fun clearData() {
            _bookmarkedQuestionList.value = emptyList()
            totalAnsweredQuestion.clear()
            _isInitializedView.value = false
            _lastPostId.value = 0L
            _hasNextQuestion.value = false
            _hasNextPost.value = false
        }
    }
}
