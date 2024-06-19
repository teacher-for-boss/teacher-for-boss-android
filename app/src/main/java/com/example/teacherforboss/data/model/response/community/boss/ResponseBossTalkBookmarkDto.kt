package com.example.teacherforboss.data.model.response.community.boss

import com.example.teacherforboss.domain.model.community.BossTalkBookmarkResponseEntity
import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDateTime

data class ResponseBossTalkBookmarkDto(
    @SerializedName("bookmark")
    val bookmark:Boolean,
    @SerializedName("updatedAt")
    val updatedAt: LocalDateTime
) {
    fun toBossTalkBookmarkResponseEntity()= BossTalkBookmarkResponseEntity(
        bookmark = bookmark,
        updatedAt = updatedAt
    )
}