package com.company.teacherforboss.domain.model.common

data class TeacherRecentAnswerListEntity(
    val recentAnswerList: List<TeacherRecentAnswer>,
) {
    data class TeacherRecentAnswer(
        val questionId: Long,
        val questionTitle: String,
        val answer: String,
        val answerLikeCount: String,
        val answeredAt: String,
    )
}
