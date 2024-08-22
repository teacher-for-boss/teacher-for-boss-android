package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.request.mypage.RequestMyPageCommentedPostsDto

data class MyPageCommentedPostsRequestEntity (
    val lastPostId:Long,
    val size:Int
){
    fun toRequestMyPageCommentedPostsDto()= RequestMyPageCommentedPostsDto(
        lastPostId=lastPostId,
        size=size
    )
}