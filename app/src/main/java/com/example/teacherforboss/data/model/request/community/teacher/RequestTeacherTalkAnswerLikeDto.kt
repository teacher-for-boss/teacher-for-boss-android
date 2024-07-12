package com.example.teacherforboss.data.model.request.community.teacher

import com.google.gson.annotations.SerializedName

data class RequestTeacherTalkAnswerLikeDto (
    @SerializedName("questionId") val questionId:Long,
    @SerializedName("answerId") val answerId:Long,
)
