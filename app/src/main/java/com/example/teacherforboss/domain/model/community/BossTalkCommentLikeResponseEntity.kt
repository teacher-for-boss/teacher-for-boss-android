package com.example.teacherforboss.domain.model.community

data class BossTalkCommentLikeResponseEntity(
    val commentId:Long,
    val liked:Boolean,
    val likedCount:Int,
    val dislikedCount:Int,
    val updatedAt:String
)

