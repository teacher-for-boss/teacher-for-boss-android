package com.example.teacherforboss.domain.model.community.boss

import com.example.teacherforboss.data.model.response.community.boss.PostDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkPostsDto
import java.io.Serializable

data class BossTalkPostsResponseEntity(
    val hasNext:Boolean,
    val postList: ArrayList<PostEntity>
) {
    fun toBossTalkResponseDto()=ResponseBossTalkPostsDto(
        hasNext=hasNext,
        postList =postList.mapTo(ArrayList()) { it.toPostDto() }
    )
}
data class PostEntity(
    val postId: Long,
    val title: String,
    val content: String,
    val bookmarkCount: Int,
    val commentCount: Int,
    val likeCount: Int,
    val liked: Boolean,
    val bookmarked: Boolean,
    val createdAt: String, //LocalDate인데가 string으로 해야 에러가 안뜸,,
): Serializable {
    fun toPostDto()=PostDto(
        postId =postId,
        title=title,
        content=content,
        bookmarkCount=bookmarkCount,
        commentCount=commentCount,
        likeCount=likeCount,
        liked=liked,
        bookmarked=bookmarked,
        createdAt = createdAt
    )
}
