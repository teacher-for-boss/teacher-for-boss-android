package com.example.teacherforboss.data.model.request.aws

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPresignedUrlDto(
    @SerializedName("type")
    val type:String,
    @SerializedName("id")
    val id:Long,
    @SerializedName("int")
    val imageCount:Int
)
