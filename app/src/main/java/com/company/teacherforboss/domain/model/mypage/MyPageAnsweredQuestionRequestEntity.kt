package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.request.mypage.RequestMyPageAnsweredQuestionDto

data class MyPageAnsweredQuestionRequestEntity (
    val lastQuestionId:Long,
    val size:Int
){
    fun toRequestMyPageAnsweredQuestionDto()= RequestMyPageAnsweredQuestionDto(
        lastQuestionId=lastQuestionId,
        size=size
    )
}