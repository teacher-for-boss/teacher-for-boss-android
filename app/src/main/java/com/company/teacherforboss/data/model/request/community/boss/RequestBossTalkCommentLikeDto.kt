package com.company.teacherforboss.data.model.request.community.boss

import com.google.gson.annotations.SerializedName

data class RequestBossTalkCommentLikeDto(
    @SerializedName("postId") val postId:Long,
    @SerializedName("commentId") val commentId:Long,
)
