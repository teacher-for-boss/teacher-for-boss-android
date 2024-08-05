package com.company.teacherforboss.data.model.request.community.boss

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestBossTalkCommentDto(
    @SerializedName("parentId")
    val parentId:Long?,
    @SerializedName("content")
    val content:String
)