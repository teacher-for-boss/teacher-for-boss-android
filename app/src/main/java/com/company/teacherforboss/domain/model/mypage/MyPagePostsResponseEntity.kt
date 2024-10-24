package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.response.community.teacher.QuestionDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkQuestionsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPageAnsweredQuestionDto
import java.io.Serializable

class MyPagePostsResponseEntity(
    val hasNext: Boolean,
    val postList: ArrayList<MyPagePostEntity>
)

