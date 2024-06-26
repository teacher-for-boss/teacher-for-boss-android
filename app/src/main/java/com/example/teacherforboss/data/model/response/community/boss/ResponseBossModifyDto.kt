package com.example.teacherforboss.data.model.response.community.boss

import com.example.teacherforboss.domain.model.community.BossTalkModifyPostResponseEntity
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
