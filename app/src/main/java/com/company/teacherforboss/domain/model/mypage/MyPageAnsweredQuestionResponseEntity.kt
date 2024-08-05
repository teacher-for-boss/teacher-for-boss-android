package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.response.community.teacher.QuestionDto
import com.company.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkQuestionsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPageAnsweredQuestionDto
import java.io.Serializable

class MyPageAnsweredQuestionResponseEntity(
    val hasNext: Boolean,
    val answeredQuestionList: ArrayList<MyPageQuestionEntity>
){
    fun toResponseMyPageAnsweredQuestionDto()= ResponseMyPageAnsweredQuestionDto(
        hasNext=hasNext,
        answeredQuestionList=answeredQuestionList.mapTo(java.util.ArrayList()) {it.toMyPageQuestionDto()}
    )
}

