package com.example.teacherforboss.data.model.request.community.boss

import com.example.teacherforboss.domain.model.community.BossTalkRequestEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestBossTalkCommentDto(
    @SerializedName("parentId")
    val parentId:Long?,
    @SerializedName("content")
    val content:String
)