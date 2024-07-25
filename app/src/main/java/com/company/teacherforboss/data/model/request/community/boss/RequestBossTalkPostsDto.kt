package com.company.teacherforboss.data.model.request.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkPostsRequestEntity
import com.google.gson.annotations.SerializedName

data class RequestBossTalkPostsDto (
    @SerializedName("lastPostId")
    val lastPostId:Long=0L,
    @SerializedName("size")
    val size:Int=10,
    @SerializedName("sortBy")
    val sortBy:String?,
    @SerializedName("keyword")
    val keyword:String?
){
    fun toRequestBossTalkPostsEntity()= BossTalkPostsRequestEntity(
        lastPostId=lastPostId,
        size=size,
        sortBy=sortBy,
        keyword=keyword
    )
}