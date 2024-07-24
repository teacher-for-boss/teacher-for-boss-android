package com.example.teacherforboss.data.model.response.mypage

import com.example.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.google.gson.annotations.SerializedName

data class ProfileResponseDto (
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileImg") val profileImg: String,
    @SerializedName("role") val role: String,
    @SerializedName("teacherInfo") val teacherInfo: TeacherInfoDto
) {

    fun toMyPageProfileEntity() = MyPageProfileEntity(
        nickname = nickname,
        profileImg = profileImg,
        role = role,
        teacherInfo = teacherInfo.toTeacherInfoEntity()
    )

    data class TeacherInfoDto (
        @SerializedName("level") val level: String,
        @SerializedName("leftAnswerCount") val leftAnswerCount: Int
    ) {
        fun toTeacherInfoEntity() = MyPageProfileEntity.TeacherInfo(
            level = level,
            leftAnswerCount = leftAnswerCount
        )
    }

}