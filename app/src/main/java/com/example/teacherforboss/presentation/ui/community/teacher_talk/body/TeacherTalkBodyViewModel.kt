package com.example.teacherforboss.presentation.ui.community.teacher_talk.body

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkAnsRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkAnsResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerLikeResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerListResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkBookmarkResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkDeleteResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkLikeResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkSelectResponseEntity
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnsUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerDislikeUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerLikeUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerListUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkBodyUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkBookmarkUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkDeleteBodyUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkLikeUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkSelectUseCase
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
    private val teacherLikeUseCase: TeacherTalkLikeUseCase,
    private val teacherTalkAnsUseCase: TeacherTalkAnsUseCase
): ViewModel() {

    private var _postAnswerLiveData=MutableLiveData<TeacherTalkAnswerResponseEntity>()
    val postAnswerLiveData:LiveData<TeacherTalkAnswerResponseEntity> get() = _postAnswerLiveData
    var _questionId=MutableLiveData<Long>().apply { value=null }
    val questionId:LiveData<Long> get()=_questionId

    var _answerId = MutableLiveData<Long>().apply { value=0L }
    val answerId: LiveData<Long> get()=_answerId

    var _title = MutableLiveData<String>("")
    val title: LiveData<String> get()=_title

    var _content = MutableLiveData<String>("")
    val content: LiveData<String> get()=_content

    private val _isLike = MutableLiveData<Boolean>().apply { value = false }
    val isLike: LiveData<Boolean> get() = _isLike

    private val _isBookmark = MutableLiveData<Boolean>().apply { value = false }
    val isBookmark: LiveData<Boolean> get() = _isBookmark

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

    private var _teacherTalkBodyBookmarkLiveData=MutableLiveData<TeacherTalkBookmarkResponseEntity>()
    val teacherTalkBodyBookmarkLiveData:LiveData<TeacherTalkBookmarkResponseEntity> get() = _teacherTalkBodyBookmarkLiveData

    private var _teacherTalkBodyLikeLiveData=MutableLiveData<TeacherTalkLikeResponseEntity>()
    val teacherTalkBodyLikeLiveData:LiveData<TeacherTalkLikeResponseEntity> get() = _teacherTalkBodyLikeLiveData

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



    fun getTeacherTalkBody(postId:Long){
        viewModelScope.launch {
            try{
                val teacherTalkBodyResponseEntity = teacherTalkBodyUseCase(
                    TeacherTalkRequestEntity(questionId = postId)
                )
                _teacherTalkBodyLiveData.value=teacherTalkBodyResponseEntity
            }catch (ex:Exception){
                throw ex
            }
        }
    }

    fun deletePost() {
        Log.d("delete", "questionId: ${questionId.toString()}")
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
                Log.d("questionId", questionId.value.toString())
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

    fun clickLikeBtn() {
        _isLike.value = _isLike.value?.not()
    }
    fun clickBookmarkBtn() {
        _isBookmark.value = _isBookmark.value?.not()
    }

    fun postBookmark(){
        clickBookmarkBtn()
        viewModelScope.launch {
            try{
                val teacherTalkBookmarkResponseEntity=teacherTalkBookmarkUseCase(
                    TeacherTalkRequestEntity(questionId=questionId.value!!)
                )
                _isBookmark.value=teacherTalkBookmarkResponseEntity.bookmarked
            }catch (ex:Exception){}
        }
    }

    fun postLike(){
        clickLikeBtn()
        viewModelScope.launch {
            try{
                val teacherTalkLikeResponseEntity=teacherTalkLikeUseCase(
                    TeacherTalkRequestEntity(questionId=questionId.value!!)
                )
                _isLike.value=teacherTalkLikeResponseEntity.liked
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