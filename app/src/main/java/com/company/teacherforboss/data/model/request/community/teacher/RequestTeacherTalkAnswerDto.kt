package com.company.teacherforboss.data.model.request.community.teacher

import com.google.gson.annotations.SerializedName

data class RequestTeacherTalkAnswerDto (
    @SerializedName("answerId") val answerId: Long
)