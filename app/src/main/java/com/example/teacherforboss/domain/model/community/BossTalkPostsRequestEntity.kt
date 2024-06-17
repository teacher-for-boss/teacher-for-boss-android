package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkPostsDto

data class BossTalkPostsRequestEntity(
    val lastPostId:Long,
    val size:Int,
    val sortBy:String?,
    val keyword:String?,

){
    fun toRequestBossTalkPostsDto()= RequestBossTalkPostsDto(
        lastPostId=lastPostId,
        size=size,
        sortBy=sortBy,
        keyword=keyword
    )
}
