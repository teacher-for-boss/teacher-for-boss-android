package com.company.teacherforboss.domain.model.community.teacher

import com.company.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkAnswerDto

data class TeacherTalkAnswerRequestEntity (
    val answerId: Long
) {
    fun toRequestTeacherAnswerDto() = RequestTeacherTalkAnswerDto(
        answerId = answerId
    )
}