package com.example.teacherforboss.data.model.response.community.boss

import com.example.teacherforboss.domain.model.community.boss.BossTalkDeletePostResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBossTalkDeletePostDto(
    @SerializedName("postId")
    val postId:Long,
    @SerializedName("updatedAt")
    val deletedAt: String?
) {
    fun toBossTalkDeletePostResponseEntity()= BossTalkDeletePostResponseEntity(
        postId = postId,
        deletedAt = deletedAt
    )
}