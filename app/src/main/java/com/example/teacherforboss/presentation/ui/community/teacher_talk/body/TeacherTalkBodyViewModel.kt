package com.example.teacherforboss.presentation.ui.community.teacher_talk.body

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkBookmarkResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkLikeResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherAnswerListResponseEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkDeleteResponseEntity
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkBodyUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkBookmarkUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkLikeUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerListUseCase
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkDeleteBodyUseCase
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

    private val teacherLikeUseCase: TeacherTalkLikeUseCase,
): ViewModel() {

    var _questionId=MutableLiveData<Long>().apply { value=0L }
    val questionId:LiveData<Long> get()=_questionId

    private val _isLike = MutableLiveData<Boolean>().apply { value = false }
    val isLike: LiveData<Boolean> get() = _isLike

    private val _isBookmark = MutableLiveData<Boolean>().apply { value = false }
    val isBookmark: LiveData<Boolean> get() = _isBookmark

    private var _tagList = MutableLiveData<ArrayList<String>>()
    val tagList:LiveData<ArrayList<String>> get()=_tagList

    var imageUrlList: List<String> = arrayListOf()

    var _isMine = MutableLiveData<Boolean>().apply { value = false }
    val isMine: LiveData<Boolean> get() =_isMine

    private var _answerList = MutableLiveData<List<TeacherAnswerListResponseEntity.AnswerEntity>>().apply { value = emptyList() }
    val answerList:LiveData<List<TeacherAnswerListResponseEntity.AnswerEntity>> get() = _answerList

    private var _teacherTalkBodyLiveData=MutableLiveData<TeacherTalkBodyResponseEntity>()
    val teacherTalkBodyLiveData:LiveData<TeacherTalkBodyResponseEntity> get() = _teacherTalkBodyLiveData

    private var _teacherTalkBodyBookmarkLiveData=MutableLiveData<TeacherTalkBookmarkResponseEntity>()
    val teacherTalkBodyBookmarkLiveData:LiveData<TeacherTalkBookmarkResponseEntity> get() = _teacherTalkBodyBookmarkLiveData

    private var _teacherTalkBodyLikeLiveData=MutableLiveData<TeacherTalkLikeResponseEntity>()
    val teacherTalkBodyLikeLiveData:LiveData<TeacherTalkLikeResponseEntity> get() = _teacherTalkBodyLikeLiveData

    private var _deleteLiveData = MutableLiveData<TeacherTalkDeleteResponseEntity>()
    val deleteLiveData: MutableLiveData<TeacherTalkDeleteResponseEntity> get() = _deleteLiveData

    private var _teacherAnswerListLiveData = MutableLiveData<TeacherAnswerListResponseEntity>()
    val teacherAnswerListLiveData: LiveData<TeacherAnswerListResponseEntity> get() = _teacherAnswerListLiveData


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

    fun deletePost(questionId: Long) {
        Log.d("delete", "questionId: ${questionId.toString()}")
        viewModelScope.launch {
            try {
                val teacherDeleteResponseEntity = teacherTalkDeleteBodyUseCase(
                    teacherTakRequestEntity = TeacherTalkRequestEntity(
                        questionId = questionId
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
                _teacherAnswerListLiveData.value = teacherTalkAnswerListResponseEntity
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

    fun setAnswerList(answerList: List<TeacherAnswerListResponseEntity.AnswerEntity>) {
        _answerList.value = answerList
    }

    fun getAnswerListValue(): List<TeacherAnswerListResponseEntity.AnswerEntity>
    =answerList.value?: emptyList<TeacherAnswerListResponseEntity.AnswerEntity>()

}