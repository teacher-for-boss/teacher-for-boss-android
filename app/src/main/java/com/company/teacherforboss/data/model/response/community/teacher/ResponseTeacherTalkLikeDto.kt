package com.company.teacherforboss.data.model.response.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkLikeResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseTeacherTalkLikeDto(
    @SerializedName("liked")
    val liked:Boolean,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("questionId")
    val questionId: Long,
){
    fun toTeacherTalkLikeResponseEntity()= TeacherTalkLikeResponseEntity(
        liked = liked,
        updatedAt = updatedAt,
        questionId = questionId,
    )
}