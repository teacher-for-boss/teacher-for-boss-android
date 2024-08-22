package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.response.community.teacher.QuestionDto
import com.company.teacherforboss.data.model.response.mypage.MyPagePostDto
import com.company.teacherforboss.data.model.response.mypage.MyPageQuestionDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MyPagePostEntity(
    val postId: Long,
    val title: String,
    val content: String,
    val bookmarkCount: Int,
    val commentCount: Int,
    val likeCount: Int,
    val liked: Boolean,
    val bookmarked: Boolean,
    val createdAt: String
)