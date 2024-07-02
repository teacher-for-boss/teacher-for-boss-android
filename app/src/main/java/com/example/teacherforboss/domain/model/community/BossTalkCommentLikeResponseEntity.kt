package com.example.teacherforboss.domain.model.community

data class BossTalkCommentLikeResponseEntity(
    val like:Boolean,
    val likeCount:Int,
    val dislikeCount:Int,
    val updatedAt:String
)

