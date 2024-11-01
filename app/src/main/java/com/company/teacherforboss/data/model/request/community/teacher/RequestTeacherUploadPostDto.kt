package com.company.teacherforboss.data.model.request.community.teacher

import com.google.gson.annotations.SerializedName

data class RequestTeacherUploadPostDto(
    @SerializedName("categoryId") val categoryId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("extraContent") val extraContent: ExtraContent?,
    @SerializedName("hashtagList") val hashtagList: List<String>,
    @SerializedName("imageUrlList") val imageUrlList: List<String>
)
data class ExtraContent(
    @SerializedName("userType") val userType: String,
    @SerializedName("secondField") val secondField: String?,
    @SerializedName("thirdField") val thirdField: String?,
    @SerializedName("fourthField") val fourthField: String?,
    @SerializedName("fifthField") val fifthField: String?,
    @SerializedName("sixthField") val sixthField: String?
)