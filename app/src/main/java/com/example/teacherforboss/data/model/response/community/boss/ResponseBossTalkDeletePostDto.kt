package com.example.teacherforboss.data.model.response.community.boss

import com.example.teacherforboss.domain.model.community.BossTalkCommentResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkDeletePostResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBossTalkDeletePostDto(
    @SerializedName("postId")
    val postId:Long,
    @SerializedName("updatedAt")
    val updatedAt: String
) {
    fun toBossTalkDeletePostResponseEntity()= BossTalkDeletePostResponseEntity(
        postId = postId,
        updatedAt = updatedAt
    )
}