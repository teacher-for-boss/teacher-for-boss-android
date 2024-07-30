package com.company.teacherforboss.data.model.response.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentLikeResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBossTalkCommentLikeDto(
    @SerializedName("commentId")
    val commentId:Long,
    @SerializedName("liked")
    val liked:Boolean,
    @SerializedName("likedCount")
    val likedCount:Int,
    @SerializedName("dislikedCount")
    val dislikedCount:Int,
    @SerializedName("updatedAt")
    val updatedAt:String
){
    fun toResponseBossTalkLikeResponseEntity()= BossTalkCommentLikeResponseEntity(
        commentId=commentId,
        liked=liked,
        likedCount=likedCount,
        dislikedCount=dislikedCount,
        updatedAt=updatedAt
    )
}

