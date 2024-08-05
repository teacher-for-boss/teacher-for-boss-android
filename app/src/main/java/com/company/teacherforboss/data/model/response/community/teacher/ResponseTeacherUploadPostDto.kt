package com.company.teacherforboss.data.model.response.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherUploadPostResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseTeacherUploadPostDto (
    @SerializedName("questionId") val questionId: Long,
    @SerializedName("createdAt") val createdAt: String
) {
    fun toTeacherUploadResponseEntity()=TeacherUploadPostResponseEntity(
        questionId = questionId,
        createdAt = createdAt
    )
}