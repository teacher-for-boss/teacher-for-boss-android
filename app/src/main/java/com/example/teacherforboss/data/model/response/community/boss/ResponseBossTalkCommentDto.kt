package com.example.teacherforboss.data.model.response.community.boss

import com.example.teacherforboss.domain.model.community.boss.BossTalkCommentResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBossTalkCommentDto(
    @SerializedName("commentId")
    val commentId:Long,
    @SerializedName("createdAt")
    val createdAt: String
) {
    fun toBossTalkCommentResponseEntity()= BossTalkCommentResponseEntity(
        commentId = commentId,
        createdAt = createdAt
    )
}