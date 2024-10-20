package com.company.teacherforboss.data.model.response.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkModifyPostResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBossModifyDto(
    @SerializedName("postId") val postId:Long,
    @SerializedName("updatedAt") val updatedAt:String
){
    fun toBossModifyPostResponseEntity()= BossTalkModifyPostResponseEntity(
        postId=postId,
        updatedAt=updatedAt
    )
}
