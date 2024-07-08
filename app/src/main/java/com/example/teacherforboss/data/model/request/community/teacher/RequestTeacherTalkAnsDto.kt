package com.example.teacherforboss.data.model.request.community.teacher

import com.example.teacherforboss.domain.model.community.TeacherTalkAnsRequestEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestTeacherTalkAnsDto(
    @SerializedName("questionId")
    val questionId:Long,
    @SerializedName("answerId")
    val answerId:Long
)