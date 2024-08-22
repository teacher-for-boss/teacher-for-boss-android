package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.request.mypage.RequestBookmarkedPostsDto

data class BookmarkedPostsRequestEntity (
    val lastPostId: Long,
    val size: Int
){
    fun toRequestBookmarkedPostsDto() = RequestBookmarkedPostsDto(
        lastPostId = lastPostId,
        size = size
    )
}