package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.request.mypage.RequestBookmarkedQuestionsDto

data class BookmarkedQuestionsRequestEntity (
    val lastQuestionId:Long,
    val size:Int,
){
    fun toRequestBookmarkedQuestionsDto() = RequestBookmarkedQuestionsDto(
        lastQuestionId = lastQuestionId,
        size = size,
    )
}