package com.example.teacherforboss.data.model.request.community.boss

import com.example.teacherforboss.domain.model.community.BossTalkPostsRequestEntity
import com.google.gson.annotations.SerializedName

data class RequestBossTalkPostsDto (

    @SerializedName("lastPostId")
    val lastPostId:Long,
    @SerializedName("size")
    val size:Int,
    @SerializedName("sortBy")
    val sortBy:String,
){
    fun toRequestBossTalkPostsEntity()=BossTalkPostsRequestEntity(
        lastPostId=lastPostId,
        size=size,
        sortBy=sortBy
    )
}