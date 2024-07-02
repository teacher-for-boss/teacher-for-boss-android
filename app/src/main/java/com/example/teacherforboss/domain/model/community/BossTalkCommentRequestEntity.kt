package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentDto


data class BossTalkCommentRequestEntity(
    val parentId:Long?,
    val content:String
){
    fun toRequestBossTalkCommentDto() = RequestBossTalkCommentDto(
        parentId = parentId,
        content = content
    )
}
