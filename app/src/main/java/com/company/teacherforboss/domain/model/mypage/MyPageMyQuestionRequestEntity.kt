package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.request.mypage.RequestMyPageAnsweredQuestionDto
import com.company.teacherforboss.data.model.request.mypage.RequestMyPageMyQuestionDto

data class MyPageMyQuestionRequestEntity (
    val lastQuestionId:Long,
    val size:Int
){
    fun toRequestMyPageMyQuestionDto()= RequestMyPageMyQuestionDto(
        lastQuestionId=lastQuestionId,
        size=size
    )
}