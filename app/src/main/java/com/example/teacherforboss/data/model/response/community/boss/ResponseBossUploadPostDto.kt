package com.example.teacherforboss.data.model.response.community.boss

import com.example.teacherforboss.domain.model.community.BossTalkUploadPostResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBossUploadPostDto(
    @SerializedName("postId") val postId:Long,
    @SerializedName("createdAt") val createdAt:String
){
    fun toBossUploadPostResponseEntity()=BossTalkUploadPostResponseEntity(
        postId=postId,
        createdAt=createdAt
    )
}
