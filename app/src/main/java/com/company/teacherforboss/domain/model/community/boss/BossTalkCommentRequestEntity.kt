package com.company.teacherforboss.domain.model.community.boss

import com.company.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentDto


data class BossTalkCommentRequestEntity(
    val parentId:Long?,
    val content:String
){
    fun toRequestBossTalkCommentDto() = RequestBossTalkCommentDto(
        parentId = parentId,
        content = content
    )
}
