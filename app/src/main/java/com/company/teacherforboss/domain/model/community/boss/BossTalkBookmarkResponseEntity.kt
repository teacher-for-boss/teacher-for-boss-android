package com.company.teacherforboss.domain.model.community.boss

import com.company.teacherforboss.data.model.response.community.boss.ResponseBossTalkBookmarkDto

data class BossTalkBookmarkResponseEntity (
    val bookmark:Boolean,
    val updatedAt:String
) {
    fun toResponseBossTalkBookmarkDto()= ResponseBossTalkBookmarkDto(
        bookmark = bookmark,
        updatedAt = updatedAt
    )
}