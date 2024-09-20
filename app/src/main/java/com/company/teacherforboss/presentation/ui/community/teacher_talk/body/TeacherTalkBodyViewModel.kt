package com.company.teacherforboss.presentation.ui.community.teacher_talk.body

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.community.MemberEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnsRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnsResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerLikeRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerLikeResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerListResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkBodyResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkBookmarkResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkDeleteResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkLikeResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkSelectResponseEntity
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnsUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerDislikeUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerLikeUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerListUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkBodyUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkBookmarkUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkDeleteBodyUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkLikeUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkSelectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherTalkBodyViewModel @Inject constructor(
    private val teacherTalkBodyUseCase: TeacherTalkBodyUseCase,
    private val teacherTalkBookmarkUseCase: TeacherTalkBookmarkUseCase,
    private val teacherTalkLikeUseCase: TeacherTalkLikeUseCase,
    private val teacherTalkDeleteBodyUseCase: TeacherTalkDeleteBodyUseCase,
    private val teacherTalkAnswerListUseCase: TeacherTalkAnswerListUseCase,
    private val teacherTalkSelectUseCase: TeacherTalkSelectUseCase,
    private val teacherTalkAnswerLikeUseCase: TeacherTalkAnswerLikeUseCase,
    private val teacherTalkAnswerDislikeUseCase: TeacherTalkAnswerDislikeUseCase,
    private val teacherTalkAnsUseCase: TeacherTalkAnsUseCase
): ViewModel() {
    private val _role = MutableLiveData<String>("")
    val role: LiveData<String>
        get() = _role

    private var _postAnswerLiveData=MutableLiveData<TeacherTalkAnswerResponseEntity>()
    val postAnswerLiveData:LiveData<TeacherTalkAnswerResponseEntity> get() = _postAnswerLiveData
    var _questionId = MutableLiveData<Long>().apply { value = 0L }
    val questionId:LiveData<Long> get()=_questionId

    var _answerId = MutableLiveData<Long>().apply { value=0L }
    val answerId: LiveData<Long> get()=_answerId

    var _title = MutableLiveData<String>("")
    val title: LiveData<String> get()=_title

    var _content = MutableLiveData<String>("")
    val content: LiveData<String> get()=_content

    private val _isLiked = MutableLiveData<Boolean>().apply { value = false }
    val isLiked: LiveData<Boolean> get() = _isLiked

    private val _isBookmarked = MutableLiveData<Boolean>().apply { value = false }
    val isBookmarked: LiveData<Boolean> get() = _isBookmarked

    private var _tagList = MutableLiveData<ArrayList<String>>()
    val tagList:LiveData<ArrayList<String>> get()=_tagList

    var imageUrlList: List<String> = arrayListOf()

    var _isMine = MutableLiveData<Boolean>().apply { value = false }
    val isMine: LiveData<Boolean> get() =_isMine

    val isSelectClicked=MutableLiveData<Unit>()

    var _isSelected = MutableLiveData<Boolean>().apply { value = false }
    val isSelected: LiveData<Boolean> get()=_isSelected

    private var _answerList = MutableLiveData<List<TeacherTalkAnswerListResponseEntity.AnswerEntity>>().apply { value = emptyList() }
    val answerList:LiveData<List<TeacherTalkAnswerListResponseEntity.AnswerEntity>> get() = _answerList

    private val _answerLikeLiveDataMap = mutableMapOf<Long, MutableLiveData<TeacherTalkAnswerLikeResponseEntity>>()

    private var _teacherTalkBodyLiveData=MutableLiveData<TeacherTalkBodyResponseEntity>()
    val teacherTalkBodyLiveData:LiveData<TeacherTalkBodyResponseEntity> get() = _teacherTalkBodyLiveData

    private var _deleteLiveData = MutableLiveData<TeacherTalkDeleteResponseEntity>()
    val deleteLiveData: MutableLiveData<TeacherTalkDeleteResponseEntity> get() = _deleteLiveData

    private var _teacherTalkAnswerListLiveData = MutableLiveData<TeacherTalkAnswerListResponseEntity>()
    val teacherTalkAnswerListLiveData: LiveData<TeacherTalkAnswerListResponseEntity> get() = _teacherTalkAnswerListLiveData

    private var _teacherSelectAnswerLiveData = MutableLiveData<TeacherTalkSelectResponseEntity>()
    val teacherSelectAnswerLiveData: LiveData<TeacherTalkSelectResponseEntity> get() = _teacherSelectAnswerLiveData

    private var _getAnswerListLiveData=MutableLiveData<TeacherTalkAnswerListResponseEntity>()
    val getAnswerListLiveData:LiveData<TeacherTalkAnswerListResponseEntity> get() = _getAnswerListLiveData

    private var _deleteAnsLiveData = MutableLiveData<TeacherTalkAnsResponseEntity>()
    val deleteAnsLiveData: MutableLiveData<TeacherTalkAnsResponseEntity> get() = _deleteAnsLiveData

    private val commentLikeLiveDataMap = mutableMapOf<Long, MutableLiveData<TeacherTalkAnswerLikeResponseEntity>>()

    val _memberId=MutableLiveData<Long>(-1L)
    val memberId: LiveData<Long> get()=_memberId

    private val _memberInfo = MutableLiveData<MemberEntity>()
    val memberInfo: LiveData<MemberEntity> get() = _memberInfo
    fun getTeacherTalkBody(postId:Long){
        viewModelScope.launch {
            try{
                val teacherTalkBodyResponseEntity = teacherTalkBodyUseCase(
                    TeacherTalkRequestEntity(questionId = postId)
                )
                _teacherTalkBodyLiveData.value=teacherTalkBodyResponseEntity
                _memberInfo.value = teacherTalkBodyResponseEntity.memberInfo.toMemberDto().toMemberEntity()
            }catch (ex:Exception){
                throw ex
            }
        }
    }

    fun deletePost() {
        viewModelScope.launch {
            try {
                val teacherDeleteResponseEntity = teacherTalkDeleteBodyUseCase(
                    teacherTakRequestEntity = TeacherTalkRequestEntity(
                        questionId = questionId.value!!
                    )
                )
                _deleteLiveData.value = teacherDeleteResponseEntity
            } catch (ex:Exception) {}
        }
    }

    fun getAnswerList() {
        viewModelScope.launch {
            try {
                val teacherTalkAnswerListResponseEntity = teacherTalkAnswerListUseCase(
                    TeacherTalkRequestEntity(questionId = questionId.value!!)
                )
                _teacherTalkAnswerListLiveData.value = teacherTalkAnswerListResponseEntity
            } catch (ex:Exception) {}
        }
    }

    fun selectAnswer() {
        viewModelScope.launch {
            try {
                val teacherTalkSelectResponseEntity = teacherTalkSelectUseCase(
                    TeacherTalkRequestEntity(questionId = questionId.value!!),
                    TeacherTalkAnswerRequestEntity(answerId = answerId.value!!)
                )
                _teacherSelectAnswerLiveData.value = teacherTalkSelectResponseEntity
            } catch (ex: Exception) {}
        }
    }
    fun deleteAnswer() {
        viewModelScope.launch {
            try {
                val teacherTalkAnsResponseEntity = teacherTalkAnsUseCase(
                    TeacherTalkAnsRequestEntity(
                        questionId = questionId.value!!,
                        answerId = answerId.value
                    )
                )
                _deleteAnsLiveData.value = teacherTalkAnsResponseEntity
            } catch (ex:Exception) {}
        }
    }

    fun postLike() {
        viewModelScope.launch {
            try {
                val response = teacherTalkLikeUseCase(TeacherTalkRequestEntity(questionId = questionId.value!!))
                val updatedLike = response.liked

                val updatedLikeCount = if (updatedLike) {
                    (teacherTalkBodyLiveData.value?.likeCount ?: 0) + 1
                } else {
                    (teacherTalkBodyLiveData.value?.likeCount ?: 1) - 1
                }

                _teacherTalkBodyLiveData.value = _teacherTalkBodyLiveData.value?.copy(
                    likeCount = updatedLikeCount,
                    liked = updatedLike
                )

                _isLiked.value = updatedLike
            } catch (ex: Exception) {
            }
        }
    }

    fun postBookmark() {
        viewModelScope.launch {
            try {
                Log.d("TeacherTalkBodyViewModel", "postBookmark() called")
                val response = teacherTalkBookmarkUseCase(TeacherTalkRequestEntity(questionId = questionId.value!!))
                Log.d("TeacherTalkBodyViewModel", "Bookmark response: $response")

                val updatedBookmark = response.bookmarked
                Log.d("TeacherTalkBodyViewModel", "updatedBookmark: $updatedBookmark")

                val updatedBookmarkCount = if (updatedBookmark) {
                    (teacherTalkBodyLiveData.value?.bookmarkCount ?: 0) + 1
                } else {
                    (teacherTalkBodyLiveData.value?.bookmarkCount ?: 1) - 1
                }
                Log.d("TeacherTalkBodyViewModel", "updatedBookmarkCount: $updatedBookmarkCount")

                _teacherTalkBodyLiveData.value = _teacherTalkBodyLiveData.value?.copy(
                    bookmarkCount = updatedBookmarkCount,
                    bookmarked = updatedBookmark
                )
                _isBookmarked.value = updatedBookmark

            } catch (ex: Exception) {
                Log.e("TeacherTalkBodyViewModel", "Error in postBookmark(): ${ex.message}")
            }
        }
    }

    fun postAnswerLike(answerId:Long){
        viewModelScope.launch {
            try{
                val teacherTalkLikeResponseEntity=teacherTalkAnswerLikeUseCase(
                    TeacherTalkAnswerLikeRequestEntity(
                        questionId = questionId.value!!,
                        answerId = answerId
                    )
                )
                _answerLikeLiveDataMap[answerId]?.value=teacherTalkLikeResponseEntity

            }catch (ex:Exception){}
        }
    }

    fun postAnswerDisLike(answerId:Long){
        viewModelScope.launch {
            try{
                val teacherTalkDisLikeResponseEntity=teacherTalkAnswerDislikeUseCase(
                    TeacherTalkAnswerLikeRequestEntity(
                        questionId = questionId.value!!,
                        answerId = answerId
                    )
                )
                _answerLikeLiveDataMap[answerId]?.value=teacherTalkDisLikeResponseEntity

            }catch (ex:Exception){}
        }
    }


    fun setTagList(tagList:ArrayList<String>){
        _tagList.value=tagList
    }

    fun getTagList(): List<String> = tagList.value?: emptyList<String>()

    fun setQuestionId(questionId: Long) {
        _questionId.value = questionId
    }

    fun setAnswerId(answerId: Long) {
        _answerId.value = answerId
    }

    fun setAnswerList(answerList: List<TeacherTalkAnswerListResponseEntity.AnswerEntity>) {
        _answerList.value = answerList
    }

    fun getAnswerListValue(): List<TeacherTalkAnswerListResponseEntity.AnswerEntity>
    =answerList.value?: emptyList<TeacherTalkAnswerListResponseEntity.AnswerEntity>()

    fun getAnswerLikeLiveData(answerId: Long): LiveData<TeacherTalkAnswerLikeResponseEntity> {
        return _answerLikeLiveDataMap.getOrPut(answerId) {MutableLiveData()}
    }

}