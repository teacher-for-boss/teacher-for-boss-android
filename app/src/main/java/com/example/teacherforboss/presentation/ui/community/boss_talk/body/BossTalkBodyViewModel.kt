package com.example.teacherforboss.presentation.ui.community.boss_talk.body

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BossTalkBodyViewModel: ViewModel() {
    val tagList = arrayListOf(
        "유저가아아아아아",
        "직접작성하는",
        "태그입니다",
        "최대5개입니다",
        "10글자까지"
    )
    val commentList = arrayListOf(
        "강아지 티쳐",
        "보스톡 글상세 티쳐",
        "김수한무거북이와두루미 티쳐"
    )

    val reCommentList = arrayListOf(
        "대댓글1 티쳐",
        "대댓글2 티쳐",
        "크루키두바이초콜릿 티쳐"
    )

    private val _isLike = MutableLiveData<Boolean>().apply { value = false }
    val isLike: LiveData<Boolean> get() = _isLike

    private val _isBookmark = MutableLiveData<Boolean>().apply { value = false }
    val isBookmark: LiveData<Boolean> get() = _isBookmark

    fun clickLikeBtn() {
        _isLike.value = _isLike.value?.not()
    }
    fun clickBookmarkBtn() {
        _isBookmark.value = _isBookmark.value?.not()
    }
}