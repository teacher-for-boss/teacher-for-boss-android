package com.example.teacherforboss.domain.model.mypage

data class MyPageProfileEntity(
    val nickname: String,
    val profileImgUrl: String,
    val role: String,
    val teacherInfo: TeacherInfo?,
) {
    data class TeacherInfo(
        val level: String,
        val leftAnswerCount: Int,
    )
}
