package com.example.teacherforboss.domain.model.community.teacher

import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkDto

data class TeacherTalkRequestEntity (
    val questionId: Long
) {
    fun toRequestTeacherTalkDto() = RequestTeacherTalkDto(
        questionId=questionId
    )
}