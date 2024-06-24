package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentDto


data class BossTalkCommentRequestEntity(
    val parentCommentId:Long,
    val content:String,
    val imageUrlList:List<String>
){
    fun toRequestBossTalkCommentDto() = RequestBossTalkCommentDto(
        parentCommentId = parentCommentId,
        content = content,
        imageUrlList = imageUrlList
    )
}
