package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.response.community.teacher.QuestionDto
import com.company.teacherforboss.data.model.response.mypage.MyPageQuestionDto
import java.io.Serializable

data class MyPageQuestionEntity(
    val questionId: Long,
    val category: String,
    val title: String,
    val content: String,
    val solved: Boolean,
    val selectedTeacher: String?,
    val createdAt: String
)