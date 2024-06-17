package com.example.teacherforboss.data.model.response.aws

import com.example.teacherforboss.domain.model.aws.presignedUrlListEntity
import com.google.gson.annotations.SerializedName

data class ResponsePresignedUrlDto (
    @SerializedName("presignedUrlList")
    val presignedUrlList:List<String>
){
    fun toPresignedUrlListEntity()= presignedUrlListEntity(
        presignedUrlList=presignedUrlList
    )
}