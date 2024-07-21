package com.example.teacherforboss.data.model.request.aws

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPresignedUrlDto(
    @SerializedName("uuid")
    val uuid:String?,
    @SerializedName("lastIndex")
    val lastIndex:Int,
    @SerializedName("int")
    val imageCount:Int,
    @SerializedName("origin")
    val origin:String,
    @SerializedName("fileType")
    val fileType:String
)
