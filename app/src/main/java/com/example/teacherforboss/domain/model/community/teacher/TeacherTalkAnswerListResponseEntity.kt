package com.example.teacherforboss.domain.model.community.teacher

import com.example.teacherforboss.domain.model.community.MemberEntity

data class TeacherTalkAnswerListResponseEntity(
    val hasNext: Boolean,
    val answerList: ArrayList<AnswerEntity>
) {
    data class AnswerEntity(
        val answerId: Long,
        val content: String,
        val likeCount: Int,
        val dislikeCount: Int,
        val liked: Boolean,
        val disliked: Boolean,
        val selected: Boolean,
        val createdAt: String,
        val memberInfo: MemberEntity,
        val imageUrlList:List<String>
    )
}