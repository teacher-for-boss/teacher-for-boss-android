package com.example.teacherforboss.domain.model.community.boss

import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBookmarkDto
import kotlinx.datetime.LocalDateTime

data class BossTalkBookmarkResponseEntity (
    val bookmark:Boolean,
    val updatedAt:String
) {
    fun toResponseBossTalkBookmarkDto()= ResponseBossTalkBookmarkDto(
        bookmark = bookmark,
        updatedAt = updatedAt
    )
}