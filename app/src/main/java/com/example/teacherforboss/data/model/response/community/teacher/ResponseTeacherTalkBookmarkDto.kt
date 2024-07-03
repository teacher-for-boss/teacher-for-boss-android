package com.example.teacherforboss.data.model.response.community.teacher

import com.example.teacherforboss.domain.model.community.TeacherTalkBookmarkResponseEntity
import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDateTime

data class ResponseTeacherTalkBookmarkDto (
    @SerializedName("bookmark")
    val bookmarked:Boolean,
    @SerializedName("updatedAt")
    val updatedAt: LocalDateTime,
    @SerializedName("questionId")
    val questionId: Long,
){
    fun toTeacherTalkBookmarkResponseEntity() = TeacherTalkBookmarkResponseEntity(
        bookmarked = bookmarked,
        updatedAt = updatedAt,
        questionId = questionId,
    )
}