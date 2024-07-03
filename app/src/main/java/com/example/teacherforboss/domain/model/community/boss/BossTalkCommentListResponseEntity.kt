package com.example.teacherforboss.domain.model.community

data class BossTalkCommentListResponseEntity(
    val totalCount:Int,
    val commentList:ArrayList<CommentEntity>
)
data class CommentEntity(
    val commentId:Long,
    val content:String,
    val likeCount:Int,
    val dislikeCount:Int,
    val createdAt:String,
    val memberInfo:MemberEntity,
    val children:ArrayList<CommentEntity>
)