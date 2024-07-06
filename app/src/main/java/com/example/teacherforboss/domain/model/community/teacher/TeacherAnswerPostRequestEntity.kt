package com.example.teacherforboss.domain.model.community.teacher

import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherAnswerPostDto

data class TeacherAnswerPostRequestEntity (
    val content: String,
    val imageUrlList: List<String>
) {
    fun toRequestTeacherAnswerPostDto() = RequestTeacherAnswerPostDto(
        content = content,
        imageUrlList = imageUrlList
    )
}