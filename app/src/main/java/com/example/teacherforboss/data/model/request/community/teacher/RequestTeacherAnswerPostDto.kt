package com.example.teacherforboss.data.model.request.community.teacher

import com.google.gson.annotations.SerializedName

data class RequestTeacherAnswerPostDto (
    @SerializedName("content") val content: String,
    @SerializedName("imageUrlList") val imageUrlList: List<String>
)