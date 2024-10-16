package com.company.teacherforboss.data.model.request.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestBossTalkDto(
    @SerializedName("postId")
    val postId:Long
){
    fun toBossTalkRequestEntity() = BossTalkRequestEntity(
        postId=postId
    )
}