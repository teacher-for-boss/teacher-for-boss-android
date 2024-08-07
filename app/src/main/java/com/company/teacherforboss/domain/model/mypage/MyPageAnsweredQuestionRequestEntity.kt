package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkQuestionsDto
import com.company.teacherforboss.data.model.response.mypage.RequestMyPageAnsweredQuestionDto

data class MyPageAnsweredQuestionRequestEntity (
    val lastQuestionId:Long,
    val size:Int
){
    fun toRequestMyPageAnsweredQuestionsDto()= RequestMyPageAnsweredQuestionDto(
        lastQuestionId=lastQuestionId,
        size=size
    )
}