package com.example.teacherforboss.data.model.response.community.teacher

import com.example.teacherforboss.domain.model.community.teacher.TeacherAnswerPostResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseTeacherAnswerPostDto (
    @SerializedName("answerId") val answerId: Long,
    @SerializedName("createdAt") val createdAt: String
) {
    fun toTeacherAnswerPostEntity() = TeacherAnswerPostResponseEntity(
        answerId = answerId,
        createdAt = createdAt
    )
}