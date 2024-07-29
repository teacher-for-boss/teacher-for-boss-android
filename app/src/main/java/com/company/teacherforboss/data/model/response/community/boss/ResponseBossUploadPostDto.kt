package com.company.teacherforboss.data.model.response.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkUploadPostResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBossUploadPostDto(
    @SerializedName("postId") val postId:Long,
    @SerializedName("createdAt") val createdAt:String
){
    fun toBossUploadPostResponseEntity()= BossTalkUploadPostResponseEntity(
        postId=postId,
        createdAt=createdAt
    )
}
