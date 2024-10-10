package com.company.teacherforboss.domain.model.community.teacher

import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkBodyDto
import com.company.teacherforboss.domain.model.community.Member

data class TeacherTalkBodyResponseEntity(
    val title:String,
    val content: String,
    val category: String,
    val imageUrlList: List<String>,
    val hashtagList: List<String>?,
    val memberInfo: Member,
    val liked: Boolean,
    val bookmarked: Boolean,
    val likeCount: Int,
    val bookmarkCount: Int,
    val answerCount: Int,
    val createdAt: String,
    val isMine: Boolean
)
