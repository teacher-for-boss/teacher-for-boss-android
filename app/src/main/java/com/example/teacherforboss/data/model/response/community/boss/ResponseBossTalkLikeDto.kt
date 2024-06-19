package com.example.teacherforboss.data.model.response.community.boss

import com.example.teacherforboss.domain.model.community.BossTalkLikeResponseEntity
import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDateTime

data class ResponseBossTalkLikeDto(
    @SerializedName("like")
    val like:Boolean,
    @SerializedName("updatedAt")
    val updatedAt: LocalDateTime
) {
    fun toBossTalkLikeResponseEntity()= BossTalkLikeResponseEntity(
        like = like,
        updatedAt = updatedAt
    )
}