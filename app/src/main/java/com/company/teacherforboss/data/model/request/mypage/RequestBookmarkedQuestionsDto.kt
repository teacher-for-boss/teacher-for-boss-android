package com.company.teacherforboss.data.model.request.mypage

import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsRequestEntity
import com.google.gson.annotations.SerializedName

data class RequestBookmarkedQuestionsDto(
    @SerializedName("lastQuestionId")
    val lastQuestionId:Long=0L,
    @SerializedName("size")
    val size:Int=10,
) {
    fun toRequestBookmarkedQuestionsEntity() = BookmarkedQuestionsRequestEntity(
        lastQuestionId = lastQuestionId,
        size = size
    )
}