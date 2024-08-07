package com.company.teacherforboss.data.model.response.mypage

import com.company.teacherforboss.domain.model.common.TeacherProfileDetailEntity
import com.google.gson.annotations.SerializedName

data class TeacherDetailProfileResponseDto (
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileImg") val profileImg: String,
    @SerializedName("introduction") val introduction: String,
    @SerializedName("phone") val phone: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("field") val field: String,
    @SerializedName("career") val career: Int,
    @SerializedName("keywords") val keywords: List<String>,
    @SerializedName("level") val level: String,
    @SerializedName("isMine") val isMine: Boolean
) {
    fun toTeacherDetailProfileResponseEntity() = TeacherProfileDetailEntity(
        nickname = nickname,
        profileImg = profileImg,
        introduction = introduction,
        phone = phone,
        email = email,
        field = field,
        career = career,
        keywords = keywords,
        level = level,
        isMine = isMine
    )
}