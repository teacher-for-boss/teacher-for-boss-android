package com.company.teacherforboss.data.model.request.community.teacher

import com.google.gson.annotations.SerializedName

data class RequestTeacherUploadPostDto (
    @SerializedName("categoryId") val categoryId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("hashtagList") val hashtagList: List<String>,
    @SerializedName("imageUrlList") val imageUrlList: List<String>
)