package com.example.teacherforboss.presentation.ui.community.teacher_talk.body

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.community.TeacherTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.TeacherTalkBookmarkResponseEntity
import com.example.teacherforboss.domain.model.community.TeacherTalkLikeResponseEntity
import com.example.teacherforboss.domain.model.community.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.usecase.TeacherTalkBodyUseCase
import com.example.teacherforboss.domain.usecase.TeacherTalkBookmarkUseCase
import com.example.teacherforboss.domain.usecase.TeacherTalkLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherTalkBodyViewModel @Inject constructor(
    private val teacherTalkBodyUseCase: TeacherTalkBodyUseCase,
    private val teacherTalkBookmarkUseCase: TeacherTalkBookmarkUseCase,
    private val teacherTalkLikeUseCase: TeacherTalkLikeUseCase,
    private val teacherLikeUseCase: TeacherTalkLikeUseCase,
): ViewModel() {

    var _questionId=MutableLiveData<Long>().apply { value=0L }
    val questionId:LiveData<Long> get()=_questionId

    private val _isLike = MutableLiveData<Boolean>().apply { value = false }
    val isLike: LiveData<Boolean> get() = _isLike

    private val _isBookmark = MutableLiveData<Boolean>().apply { value = false }
    val isBookmark: LiveData<Boolean> get() = _isBookmark

    private var _teacherTalkBodyLiveData=MutableLiveData<TeacherTalkBodyResponseEntity>()
    val teacherTalkBodyLiveData:LiveData<TeacherTalkBodyResponseEntity> get() = _teacherTalkBodyLiveData

    private var _teacherTalkBodyBookmarkLiveData=MutableLiveData<TeacherTalkBookmarkResponseEntity>()
    val teacherTalkBodyBookmarkLiveData:LiveData<TeacherTalkBookmarkResponseEntity> get() = _teacherTalkBodyBookmarkLiveData

    private var _teacherTalkBodyLikeLiveData=MutableLiveData<TeacherTalkLikeResponseEntity>()
    val teacherTalkBodyLikeLiveData:LiveData<TeacherTalkLikeResponseEntity> get() = _teacherTalkBodyLikeLiveData


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

    var tagList:ArrayList<String>? = arrayListOf()

    data class Answer(
        val content: String,
        val likeCount: Int,
        val dislikeCount: Int,
        val like: Boolean,
        val dislike: Boolean,
        val selected: Boolean,
        val name: String
    )

    val answerList = listOf(
        Answer("내용1", 10, 10, false, false, false, "윤희재짱짱 티쳐"),
        Answer("내용1", 10, 10, false, false, false, "하치와레 티쳐"),
        Answer("내용1", 10, 10, false, false, false, "아야어여오요 티쳐")
    )
}