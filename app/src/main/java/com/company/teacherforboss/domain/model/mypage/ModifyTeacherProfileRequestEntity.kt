package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.request.mypage.ModifyTeacherProfileRequestDto

data class ModifyTeacherProfileRequestEntity (
    val nickname: String,
    val phone: String?,
    val phoneOpen: Boolean,
    val email: String?,
    val emailOpen: Boolean,
    val field: String,
    val career: Int,
    val introduction: String,
    val keywords: List<String>,
    val profileImg: String
) {
    fun toModifyTeacherProfileRequestDto() = ModifyTeacherProfileRequestDto(
        nickname = nickname,
        phone = phone,
        phoneOpen = phoneOpen,
        email = email,
        emailOpen = emailOpen,
        field = field,
        career = career,
        introduction = introduction,
        keywords = keywords,
        profileImg = profileImg
    )
}