package com.company.teacherforboss.data.model.response.mypage.teacher_detail_profile

import com.company.teacherforboss.domain.model.common.TeacherRecentAnswerListEntity
import com.google.gson.annotations.SerializedName

data class TeacherRecentAnswersResponseDto (
    @SerializedName("recentAnswerList") val recentAnswerList: List<RecentAnswerDto>
) {

    fun toTeacherRecentAnswerListEntity() = TeacherRecentAnswerListEntity(
        recentAnswerList = recentAnswerList.map { it.toTeacherRecentAnswerEntity() } as ArrayList<TeacherRecentAnswerListEntity.TeacherRecentAnswer>
    )

    data class RecentAnswerDto (
        @SerializedName("questionId") val questionId: Long,
        @SerializedName("questionTitle") val questionTitle: String,
        @SerializedName("answer") val answer: String,
        @SerializedName("answerLikeCount") val answerLikeCount: Int,
        @SerializedName("answeredAt") val answeredAt: String
    ) {
        fun toTeacherRecentAnswerEntity() = TeacherRecentAnswerListEntity.TeacherRecentAnswer(
            questionId = questionId,
            questionTitle = questionTitle,
            answer = answer,
            answerLikeCount = answerLikeCount,
            answeredAt = answeredAt
        )
    }
}