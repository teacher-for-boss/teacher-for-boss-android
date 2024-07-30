package com.company.teacherforboss.domain.model.mypage

data class MyPageProfileEntity(
    val nickname: String,
    val profileImg: String,
    val role: String,
    val teacherInfo: TeacherInfo?,
) {
    data class TeacherInfo(
        val level: String,
        val leftAnswerCount: Int,
    )
}
