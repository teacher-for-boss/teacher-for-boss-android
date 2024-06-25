package com.example.teacherforboss.domain.model.community

import com.google.gson.annotations.SerializedName

data class BossTalkCommentListResponseEntity(
    val totalCount:Long,
    val commentList:ArrayList<CommentEntity>
)
data class CommentEntity(
    val commentId:Long,
    val content:String,
    val likeCount:Long,
    val dislikeCount:Int,
    val createdAt:String,
    val memberInfo:ArrayList<MemberEntity>,
    val children:ArrayList<CommentEntity>
)