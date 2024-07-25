package com.company.teacherforboss.data.model.request.community.teacher

import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestTeacherTalkDto(
    @SerializedName("questionId")
    val questionId:Long
) {
   fun toTeacherTalkRequestEntity() = TeacherTalkRequestEntity(
       questionId = questionId
   )
}