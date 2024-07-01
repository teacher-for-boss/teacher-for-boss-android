package com.example.teacherforboss.presentation.ui.community.teacher_talk.body

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.community.TeacherTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.TeacherTalkRequestEntity
import com.example.teacherforboss.domain.usecase.TeacherTalkBodyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherTalkBodyViewModel @Inject constructor(
    private val teacherTalkBodyUseCase: TeacherTalkBodyUseCase,
): ViewModel() {

    private val _isLike = MutableLiveData<Boolean>().apply { value = false }
    val isLike: LiveData<Boolean> get() = _isLike

    private val _isBookmark = MutableLiveData<Boolean>().apply { value = false }
    val isBookmark: LiveData<Boolean> get() = _isBookmark

    private var _teacherTalkBodyLiveData=MutableLiveData<TeacherTalkBodyResponseEntity>()
    val teacherTalkBodyLiveData:LiveData<TeacherTalkBodyResponseEntity> get() = _teacherTalkBodyLiveData
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

    val tagList = arrayListOf(
        "유저가",
        "직접작성하는",
        "태그입니다",
        "여러줄에걸쳐서",
        "나타나도록했어요"
    )

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