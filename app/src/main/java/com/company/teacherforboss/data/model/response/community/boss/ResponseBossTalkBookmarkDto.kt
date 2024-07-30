package com.company.teacherforboss.data.model.response.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkBookmarkResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBossTalkBookmarkDto(
    @SerializedName("bookmark")
    val bookmark:Boolean,
    @SerializedName("updatedAt")
    val updatedAt:String
) {
    fun toBossTalkBookmarkResponseEntity()= BossTalkBookmarkResponseEntity(
        bookmark = bookmark,
        updatedAt = updatedAt
    )
}