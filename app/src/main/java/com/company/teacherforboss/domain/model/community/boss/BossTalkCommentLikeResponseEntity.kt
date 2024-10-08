package com.company.teacherforboss.domain.model.community.boss

data class BossTalkCommentLikeResponseEntity(
    val commentId:Long,
    val liked:Boolean?,
    val likedCount:Int,
    val dislikedCount:Int,
    val updatedAt:String
)

