package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkLikeDto
import kotlinx.datetime.LocalDateTime

data class BossTalkLikeResponseEntity (
    val like:Boolean,
    val updatedAt:String
) {
    fun toResponseBossTalkLikeDto()= ResponseBossTalkLikeDto(
        like = like,
        updatedAt = updatedAt
    )
}