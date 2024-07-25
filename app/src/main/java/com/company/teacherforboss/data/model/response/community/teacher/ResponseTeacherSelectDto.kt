package com.company.teacherforboss.data.model.response.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkSelectResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseTeacherSelectDto (
    @SerializedName("selectedAnswerId") val selectedAnswerId: Long,
    @SerializedName("updatedAt") val updatedAt: String
) {
    fun toTeacherTalkSelectResponseEntity() = TeacherTalkSelectResponseEntity(
        selectedAnswerId = selectedAnswerId,
        updatedAt = updatedAt
    )
}