package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.request.mypage.RequestMyPageMyPostsDto

data class MyPageMyPostsRequestEntity (
    val lastPostId:Long,
    val size:Int
){
    fun toRequestMyPageMyPostsDto()= RequestMyPageMyPostsDto(
        lastPostId=lastPostId,
        size=size
    )
}