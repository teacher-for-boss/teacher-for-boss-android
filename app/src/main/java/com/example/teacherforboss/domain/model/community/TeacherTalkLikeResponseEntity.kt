package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkLikeDto
import kotlinx.datetime.LocalDateTime

data class TeacherTalkLikeResponseEntity (
    val liked: Boolean,
    val updatedAt: LocalDateTime,
    val questionId: Long,
){
    fun toResponseTeacherTalkLikeDto() = ResponseTeacherTalkLikeDto(
        liked = liked,
        updatedAt = updatedAt,
        questionId = questionId,
    )
}