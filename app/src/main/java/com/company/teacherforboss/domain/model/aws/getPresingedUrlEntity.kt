package com.company.teacherforboss.domain.model.aws

import com.company.teacherforboss.data.model.request.aws.RequestPresignedUrlDto

data class getPresingedUrlEntity(
    val uuid:String?,
    val lastIndex:Int,
    val imageCount:Int,
    val origin:String
){
    fun toGetPresignedUrlDto()= RequestPresignedUrlDto(
        uuid=uuid?:null,
        lastIndex=lastIndex,
        imageCount=imageCount,
        origin=origin
    )
}
