package com.example.teacherforboss.domain.model.community.boss

import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentLikeDto

data class BossTalkCommentLikeRequestEntity(
    val postId:Long,
    val commentId:Long
){
    fun toBossTalkCommentLikeDto()=RequestBossTalkCommentLikeDto(
        postId = postId,
        commentId=commentId
    )
}