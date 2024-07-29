package com.company.teacherforboss.domain.model.community.teacher

import com.company.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkAnsDto

data class TeacherTalkAnsRequestEntity(
    val questionId:Long,
    val answerId:Long?
){
    fun toRequestTeacherTalkAnsDto() = RequestTeacherTalkAnsDto(
        questionId = questionId,
        answerId = answerId
    )
}