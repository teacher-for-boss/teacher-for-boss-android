package com.company.teacherforboss.domain.model.community

data class BossTalkCommentListResponseEntity(
    val hasNext:Boolean,
    val commentList:ArrayList<CommentEntity>
)
data class CommentEntity(
    val commentId:Long,
    val content:String,
    val likeCount:Int,
    val dislikeCount:Int,
    val liked:Boolean,
    val disliked:Boolean,
    val createdAt:String,
    val memberInfo:MemberEntity?,
    val children:ArrayList<CommentEntity>,
    val isMine: Boolean,
    val deleted: Boolean
)