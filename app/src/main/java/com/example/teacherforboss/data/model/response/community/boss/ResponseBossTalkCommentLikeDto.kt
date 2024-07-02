package com.example.teacherforboss.data.model.response.community.boss

import com.example.teacherforboss.domain.model.community.BossTalkCommentLikeResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBossTalkCommentLikeDto(
@SerializedName("like")
val like:Boolean,
@SerializedName("likeCount")
val likeCount:Int,
@SerializedName("dislikeCount")
val dislikeCount:Int,
@SerializedName("updatedAt")
val updatedAt:String
){
    fun toResponseBossTalkLikeResponseEntity()=BossTalkCommentLikeResponseEntity(
        like=like,
        likeCount=likeCount,
        dislikeCount=dislikeCount,
        updatedAt=updatedAt
    )
}

