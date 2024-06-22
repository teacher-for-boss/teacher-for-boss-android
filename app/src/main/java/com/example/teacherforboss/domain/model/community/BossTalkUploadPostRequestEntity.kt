package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.request.community.boss.RequestBossUploadPostDto

data class BossTalkUploadPostRequestEntity(
    val title:String,
    val content:String,
    val imageUrlList:List<String>,
    val hashtagList:List<String>
){
    fun toBossUploadRequestDto()=RequestBossUploadPostDto(
        title=title,
        content=content,
        imageUrlList=imageUrlList,
        hashtagList=hashtagList
    )
}