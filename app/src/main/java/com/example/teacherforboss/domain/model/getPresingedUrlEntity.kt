package com.example.teacherforboss.domain.model

import com.example.teacherforboss.data.model.request.aws.RequestPresignedUrlDto

data class getPresingedUrlEntity(
    val type:String,
    val id:Long,
    val imageCount:Int,
){
    fun toGetPresignedUrlDto()= RequestPresignedUrlDto(
        type=type,
        id=id,
        imageCount=imageCount
    )
}
