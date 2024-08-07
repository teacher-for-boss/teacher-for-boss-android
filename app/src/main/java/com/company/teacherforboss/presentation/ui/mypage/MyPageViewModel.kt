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

    val _nickname = MutableLiveData<String>("")
    val nickname: LiveData<String> get() = _nickname

    val _profileImg = MutableLiveData<String>("")
    val profileImg: LiveData<String> get() = _profileImg

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

    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun setProfileImg(img: String) {
        _profileImg.value = img
      
    fun getBookmarkedQuestions() {
        viewModelScope.launch {
//            try {
//                val bookmarkedQuestionsResponseEntity = bookmarkedQuestionsUseCase()
//                _bookmarkedQuestionsState.value = UiState.Success(bookmarkedQuestionsResponseEntity)
//            } catch (ex:Exception) { _bookmarkedQuestionsState.value = UiState.Error(ex.message) }
            val dummyData = BookmarkedQuestionsResponseEntity(
                hasNext = false,
                bookmarkedQuestionsList = arrayListOf(
                    BookmarkedQuestionsEntity(
                        questionId = 124,
                        category = "운영",
                        title = "음식사진",
                        content = "음식사진을 이렇게 찍어도 될까요?",
                        solved = false,
                        selectedTeacher = "https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png",
                        createdAt = "2024-07-22T10:34:43.845768"
                    ),
                    BookmarkedQuestionsEntity(
                        questionId = 112,
                        category = "마케팅",
                        title = "마켙팅에 대한 글을 작성합니다. 보스테스트 계정",
                        content = "보스테스트 계정",
                        solved = true,
                        selectedTeacher = "https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png",
                        createdAt = "2024-07-19T16:57:46.547503"
                    ),
                    BookmarkedQuestionsEntity(
                        questionId = 24,
                        category = "위생",
                        title = "위생에 관한 글 주저리주저리",
                        content = "위생은 중요하다.",
                        solved = false,
                        selectedTeacher = "https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png",
                        createdAt = "2024-07-05T18:22:31.51143"
                    ),
                    BookmarkedQuestionsEntity(
                        questionId = 26,
                        category = "위생",
                        title = "티쳐톡 질문하기 제목입니다.",
                        content = "티쳐톡 질문하기 글 본문입니다.",
                        solved = true,
                        selectedTeacher = "https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png",
                        createdAt = "2024-07-05T18:43:43.793877"
                    ),
                    BookmarkedQuestionsEntity(
                        questionId = 110,
                        category = "상권",
                        title = "서울에서 카페를 어디에 차릴까요",
                        content = "새로운 카페를 차리려하는데 어디에 차려야 좋을까요?",
                        solved = true,
                        selectedTeacher = "https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png",
                        createdAt = "2024-07-18T13:40:48.688842"
                    ),
                    BookmarkedQuestionsEntity(
                        questionId = 1,
                        category = "마케팅",
                        title = "티쳐톡 테스트 1차 수정",
                        content = "난 이미 지쳤어요 땡벌 땡벌!",
                        solved = true,
                        selectedTeacher = "https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png",
                        createdAt = "2024-07-16T15:38:43"
                    ),
                    BookmarkedQuestionsEntity(
                        questionId = 61,
                        category = "마케팅",
                        title = "제목",
                        content = "ㅇㅇ오모모모모목",
                        solved = true,
                        selectedTeacher = "https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png",
                        createdAt = "2024-07-05T22:45:49.620137"
                    ),
                    BookmarkedQuestionsEntity(
                        questionId = 99,
                        category = "마케팅",
                        title = "제제제",
                        content = "목목ㅁ곰고",
                        solved = false,
                        selectedTeacher = "https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png",
                        createdAt = "2024-07-08T21:53:21.322096"
                    ),
                    BookmarkedQuestionsEntity(
                        questionId = 98,
                        category = "마케팅",
                        title = "제목",
                        content = "본문",
                        solved = true,
                        selectedTeacher = "https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png",
                        createdAt = "2024-07-08T21:50:54.30989"
                    ),
                    BookmarkedQuestionsEntity(
                        questionId = 67,
                        category = "마케팅",
                        title = "ㅇ2",
                        content = "ㅇ",
                        solved = true,
                        selectedTeacher = "https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png",
                        createdAt = "2024-07-05T23:25:28.569501"
                    )
                )
            )

            _bookmarkedQuestion.value = dummyData.bookmarkedQuestionsList
            _bookmarkedQuestionsState.value = UiState.Success(dummyData)
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