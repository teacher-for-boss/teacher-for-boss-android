package com.example.teacherforboss.domain.model.community

import com.example.teacherforboss.data.model.response.community.boss.PostDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkPostsDto

data class BossTalkPostsResponseEntity(
    val totalCount:Int,
    val postList: ArrayList<PostEntity>
) {
    fun toBossTalkResponseDto()=ResponseBossTalkPostsDto(
        totalCount =totalCount,
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
    val like: Boolean,
    val bookmark: Boolean,
    val createdAt: String, //LocalDate인데가 string으로 해야 에러가 안뜸,,
){
    fun toPostDto()=PostDto(
        postId =postId,
        title=title,
        content=content,
        bookmarkCount=bookmarkCount,
        commentCount=commentCount,
        likeCount=likeCount,
        like=like,
        bookmark=bookmark,
        createdAt = createdAt
    )
}
