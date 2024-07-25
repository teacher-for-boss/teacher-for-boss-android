package com.company.teacherforboss.data.model.response.community.teacher

import com.company.teacherforboss.data.model.response.community.MemberDto
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerListResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseTeacherAnswerListDto (
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("answerList") val answerList: ArrayList<AnswerDto>
) {
    fun toTeacherAnswerListResponseEntity() = TeacherTalkAnswerListResponseEntity(
        hasNext = hasNext,
        answerList = answerList.map { it.toAnswerEntity() } as ArrayList<TeacherTalkAnswerListResponseEntity.AnswerEntity>
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
        @SerializedName("memberInfo") val memberInfo: MemberDto,
        @SerializedName("imageUrlList") val imageUrlList: List<String>,
        @SerializedName("isMine") val isMine: Boolean
    ) {
        fun toAnswerEntity() = TeacherTalkAnswerListResponseEntity.AnswerEntity(
            answerId = answerId,
            content = content,
            likeCount = likeCount,
            dislikeCount = dislikeCount,
            liked = liked,
            disliked = disliked,
            selected = selected,
            createdAt = createdAt,
            memberInfo = memberInfo.toMemberEntity(),
            imageUrlList = imageUrlList,
            isMine = isMine
        )
    }
}