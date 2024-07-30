package com.company.teacherforboss.domain.model.community.teacher

import com.company.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkAnswerLikeDto

data class TeacherTalkAnswerLikeRequestEntity(
    val questionId:Long,
    val answerId:Long,
) {
    fun toTeacherTalkAnswerLikeDto()=RequestTeacherTalkAnswerLikeDto(
        questionId=questionId,
        answerId=answerId
    )
}