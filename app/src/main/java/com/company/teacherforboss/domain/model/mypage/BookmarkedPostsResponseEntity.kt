package com.company.teacherforboss.domain.model.mypage

import com.company.teacherforboss.data.model.response.mypage.BookmarkedPostsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedPostsDto
import kotlinx.serialization.Serializable


data class BookmarkedPostsResponseEntity (
    val hasNext:Boolean,
    val postList: ArrayList<BookmarkedPostsEntity>
) {
    fun toBookmarkedPostsResponseDto() = ResponseBookmarkedPostsDto(
        hasNext = hasNext,
        postList = postList.mapTo(ArrayList()) { it.toBookmarkedPostsDto() }
    )
}

data class BookmarkedPostsEntity(
    val postId:Long,
    val title: String,
    val content: String,
    val bookmarkCount: Int,
    val commentCount: Int,
    val likeCount: Int,
    val liked: Boolean,
    val bookmarked: Boolean,
    val createdAt: String
) {
    fun toBookmarkedPostsDto() = BookmarkedPostsDto(
        postId=postId,
        title=title,
        content=content,
        bookmarkCount=bookmarkCount,
        commentCount=commentCount,
        likeCount=likeCount,
        liked=liked,
        bookmarked=bookmarked,
        createdAt=createdAt
    )
}