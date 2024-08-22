package com.company.teacherforboss.data.model.response.mypage

import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBookmarkedPostsDto (
    @SerializedName("hasNext")
    val hasNext:Boolean,
    @SerializedName("postList")
    val postList: ArrayList<BookmarkedPostsDto>
){
    fun toBookmarkedPostsEntity(): BookmarkedPostsResponseEntity {
        val postEntities = postList.mapTo(ArrayList()){ it.toBookmarkedPostsEntity() }
        return BookmarkedPostsResponseEntity(
            hasNext = hasNext,
            postList = postEntities
        )
    }
}

data class BookmarkedPostsDto(
    @SerializedName("postId")
    val postId: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("bookmarkCount")
    val bookmarkCount: Int,
    @SerializedName("commentCount")
    val commentCount: Int,
    @SerializedName("likeCount")
    val likeCount: Int,
    @SerializedName("liked")
    val liked: Boolean,
    @SerializedName("bookmarked")
    val bookmarked: Boolean,
    @SerializedName("createdAt")
    val createdAt: String
) {
    fun toBookmarkedPostsEntity() = BookmarkedPostsEntity(
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