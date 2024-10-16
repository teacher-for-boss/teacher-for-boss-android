package com.company.teacherforboss.data.model.response.community.boss

import com.company.teacherforboss.domain.model.community.BossTalkCommentListResponseEntity
import com.company.teacherforboss.domain.model.community.CommentEntity
import com.google.gson.annotations.SerializedName

data class ResponseBossTalkCommentListDto(
    @SerializedName("totalCount")
    val totalCount:Int,
    @SerializedName("commentList")
    val commentList:ArrayList<CommentDto>
) {
    fun toBossTalkCommentListResponseEntity(): BossTalkCommentListResponseEntity {
        val commentEntities = commentList.mapTo(ArrayList()) { it.toCommentEntity() }
        return BossTalkCommentListResponseEntity(
            totalCount = totalCount,
            commentList = commentEntities
        )
    }

}
data class CommentDto(
    @SerializedName("commentId")
    val commentId:Long,
    @SerializedName("content")
    val content:String,
    @SerializedName("likeCount")
    val likeCount:Int,
    @SerializedName("dislikeCount")
    val dislikeCount:Int,
    @SerializedName("liked")
    val liked:Boolean,
    @SerializedName("disliked")
    val disliked:Boolean,
    @SerializedName("createdAt")
    val createdAt:String,
    @SerializedName("memberInfo")
    val memberInfo:MemberDto,
    @SerializedName("children")
    val children:ArrayList<CommentDto>,
    @SerializedName("isMine")
    val isMine: Boolean
){
    fun toCommentEntity(): CommentEntity {
        val memberEntities = memberInfo.toMemberEntity()
        val children = children.mapTo(ArrayList()) { it.toCommentEntity() }
        return CommentEntity(
            commentId=commentId,
            content=content,
            likeCount=likeCount,
            dislikeCount=dislikeCount,
            liked=liked,
            disliked=disliked,
            createdAt=createdAt,
            memberInfo=memberEntities,
            children=children,
            isMine = isMine
        )
    }


}