package com.example.teacherforboss.data.model.response.home

import com.example.teacherforboss.domain.model.home.TeacherTalkPopularPostEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTeacherTalkPopularPostDto(
    @SerialName("hotQuestionList")
    val hotQuestionList: List<HotQuestionList>,
) {
    @Serializable
    data class HotQuestionList(
        @SerialName("questionId")
        val questionId: Long,
        @SerialName("category")
        val category: String,
        @SerialName("title")
        val title: String,
        @SerialName("content")
        val content: String,
        @SerialName("answerCount")
        val answerCount: Int,
    ) {
        fun toTeacherTalkPopularPostEntity() = TeacherTalkPopularPostEntity(
            id = questionId,
            category = category,
            title = title,
            content = content,
            comment = answerCount.toString(),
        )
    }
}
