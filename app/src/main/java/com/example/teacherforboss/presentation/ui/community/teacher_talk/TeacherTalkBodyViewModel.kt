package com.example.teacherforboss.presentation.ui.community.teacher_talk

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeacherTalkBodyViewModel: ViewModel() {

    private val _isLike = MutableLiveData<Boolean>().apply { value = false }
    val isLike: LiveData<Boolean> get() = _isLike

    private val _isBookmark = MutableLiveData<Boolean>().apply { value = false }
    val isBookmark: LiveData<Boolean> get() = _isBookmark

    private val _isAnswerGood = MutableLiveData<Boolean>().apply { value = false }
    val isAnswerGood: LiveData<Boolean> get() = _isAnswerGood

    private val _isAnswerBad = MutableLiveData<Boolean>().apply { value = false }
    val isAnswerBad: LiveData<Boolean> get() = _isAnswerBad

    fun clickLikeBtn() {
        _isLike.value = _isLike.value?.not()
    }
    fun clickBookmarkBtn() {
        _isBookmark.value = _isBookmark.value?.not()
    }

    fun clickAnswerGood() {
        _isAnswerGood.value = _isAnswerGood.value?.not()

        val isGood = isAnswerGood.value ?: false
        val isBad = isAnswerBad.value ?: false
        if(isGood && isBad) {
            _isAnswerBad.value = _isAnswerBad.value?.not()
        }

        Log.d("Answer", "isGood: ${_isAnswerGood.value}")
        Log.d("Answer", "isBad: ${_isAnswerBad.value}")
    }

    fun clickAnswerBad() {
        _isAnswerBad.value = _isAnswerBad.value?.not()

        val isGood = isAnswerGood.value ?: false
        val isBad = isAnswerBad.value ?: false
        if(isGood && isBad) {
            _isAnswerGood.value = _isAnswerGood.value?.not()
        }

        Log.d("Answer", "isGood: ${_isAnswerGood.value}")
        Log.d("Answer", "isBad: ${_isAnswerBad.value}")
    }

    val tagList = arrayListOf(
        "유저가",
        "직접작성하는",
        "태그입니다"
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