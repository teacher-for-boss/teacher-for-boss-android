package com.company.teacherforboss.data.model.response.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkDeleteResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseTeacherDeleteDto (
    @SerializedName("questionId") val questionId: Long,
    @SerializedName("deletedAt") val deletedAt: String
) {
    fun toTeacherDeleteResponseEntity() = TeacherTalkDeleteResponseEntity(
        questionId = questionId,
        deletedAt = deletedAt
    )
}