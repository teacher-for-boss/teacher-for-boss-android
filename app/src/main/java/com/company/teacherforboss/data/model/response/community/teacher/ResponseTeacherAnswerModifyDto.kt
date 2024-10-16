package com.company.teacherforboss.data.model.response.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherAnswerModifyResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseTeacherAnswerModifyDto(
    @SerializedName("answerId") val answerId: Long,
    @SerializedName("updatedAt") val updatedAt: String
) {
    fun toTeacherAnswerModifyResponseEntity() = TeacherAnswerModifyResponseEntity(
        answerId = answerId,
        updatedAt = updatedAt
    )
}