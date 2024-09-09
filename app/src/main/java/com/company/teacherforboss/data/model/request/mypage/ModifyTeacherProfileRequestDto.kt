package com.company.teacherforboss.data.model.request.mypage

import com.google.gson.annotations.SerializedName

data class ModifyTeacherProfileRequestDto (
    @SerializedName("nickname") val nickname: String,
    @SerializedName("phone") val phone: String?,
    @SerializedName("phoneOpen") val phoneOpen: Boolean,
    @SerializedName("email") val email: String?,
    @SerializedName("emailOpen") val emailOpen: Boolean,
    @SerializedName("field") val field: String,
    @SerializedName("career") val career: Int,
    @SerializedName("introduction") val introduction: String,
    @SerializedName("keywords") val keywords: List<String>,
    @SerializedName("profileImg") val profileImg: String
)