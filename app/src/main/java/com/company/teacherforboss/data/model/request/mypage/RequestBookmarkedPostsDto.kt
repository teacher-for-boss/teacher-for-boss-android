package com.company.teacherforboss.data.model.request.mypage

import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsRequestEntity
import com.google.gson.annotations.SerializedName

data class RequestBookmarkedPostsDto (
    @SerializedName("lastPostId")
    val lastPostId:Long=0L,
    @SerializedName("size")
    val size:Int=10,)
{
    fun toRequestBookmarkedPostsEntity() = BookmarkedPostsRequestEntity(
        lastPostId = lastPostId,
        size = size
    )
}