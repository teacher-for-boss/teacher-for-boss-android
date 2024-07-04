package com.example.teacherforboss.data.model.response.community.teacher

import com.example.teacherforboss.data.model.response.community.MemberDto
import com.example.teacherforboss.domain.model.community.teacher.TeacherAnswerListResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseTeacherAnswerListDto (
    @SerializedName("totalCount") val totalCount: Int,
    @SerializedName("answerList") val answerList: ArrayList<AnswerDto>
) {
    fun toTeacherAnswerListResponseEntity() = TeacherAnswerListResponseEntity(
        totalCount = totalCount,
        answerList = answerList.map { it.toAnswerEntity() } as ArrayList<TeacherAnswerListResponseEntity.AnswerEntity>
    )
    data class AnswerDto(
        @SerializedName("answerId") val answerId: Long,
        @SerializedName("content") val content: String,
        @SerializedName("likeCount") val likeCount: Int,
        @SerializedName("dislikeCount") val dislikeCount: Int,
        @SerializedName("liked") val liked: Boolean,
        @SerializedName("disliked") val disliked: Boolean,
        @SerializedName("selected") val selected: Boolean,
        @SerializedName("createdAt") val createdAt: String,
        @SerializedName("memberInfo") val memberInfo: MemberDto
    ) {
        fun toAnswerEntity() = TeacherAnswerListResponseEntity.AnswerEntity(
            answerId = answerId,
            content = content,
            likeCount = likeCount,
            dislikeCount = dislikeCount,
            liked = liked,
            disliked = disliked,
            selected = selected,
            createdAt = createdAt,
            memberInfo = memberInfo.toMemberEntity()
        )
    }
}