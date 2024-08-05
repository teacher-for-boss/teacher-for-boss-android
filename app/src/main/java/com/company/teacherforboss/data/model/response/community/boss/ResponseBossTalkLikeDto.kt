package com.company.teacherforboss.data.model.response.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkLikeResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBossTalkLikeDto(
    @SerializedName("like")
    val like:Boolean,
    @SerializedName("updatedAt")
    val updatedAt:String
) {
    fun toBossTalkLikeResponseEntity()= BossTalkLikeResponseEntity(
        like = like,
        updatedAt = updatedAt
    )
}