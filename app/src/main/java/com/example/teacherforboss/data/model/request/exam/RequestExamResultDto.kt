package com.example.teacherforboss.data.model.request.exam

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
@Serializable
data class RequestExamResultDto(
    @SerializedName("examId")
    val examId:Int
)
