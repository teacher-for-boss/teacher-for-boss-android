package com.company.teacherforboss.data.model.response.mypage

import com.company.teacherforboss.domain.model.mypage.MyPagePostEntity
import com.company.teacherforboss.domain.model.mypage.MyPageQuestionEntity
import com.google.gson.annotations.SerializedName

data class MyPagePostDto(
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
)
{
    fun toMyPagePostEntity()= MyPagePostEntity(
        postId = postId,
        title = title,
        content = content,
        bookmarkCount = bookmarkCount,
        commentCount = commentCount,
        likeCount = likeCount,
        liked = liked,
        bookmarked = bookmarked,
        createdAt = createdAt
    )
}