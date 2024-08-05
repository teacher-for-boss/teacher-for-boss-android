package com.company.teacherforboss.data.model.response.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkModifyResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseTeacherModifyDto (
    @SerializedName("questionId") val questionId: Long,
    @SerializedName("updatedAt") val updatedAt: String
) {
    fun toTeacherModifyResponseEntity() = TeacherTalkModifyResponseEntity(
        questionId = questionId,
        updatedAt = updatedAt
    )
}