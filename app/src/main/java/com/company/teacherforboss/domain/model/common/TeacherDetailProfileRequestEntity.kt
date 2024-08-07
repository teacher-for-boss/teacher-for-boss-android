package com.company.teacherforboss.domain.model.common

import com.company.teacherforboss.data.model.request.mypage.TeacherDetailProfileRequestDto

data class TeacherDetailProfileRequestEntity (
    val memberId: Long?
) {
    fun toTeacherDetailProfileRequestDto() = TeacherDetailProfileRequestDto(
        memberId = memberId
    )
}