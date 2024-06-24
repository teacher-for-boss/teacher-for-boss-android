package com.example.teacherforboss.data.model.response.community.boss

import com.example.teacherforboss.domain.model.community.BossTalkPostsResponseEntity
import com.example.teacherforboss.domain.model.community.PostEntity
import com.google.gson.annotations.SerializedName

data class ResponseBossTalkPostsDto(
    @SerializedName("hasNext")
    val hasNext:Boolean,
    @SerializedName("postList")
    val postList: ArrayList<PostDto>
){
    fun toBossTalkPostsEntity():BossTalkPostsResponseEntity{
        val postEntities = postList.mapTo(ArrayList()) { it.toPostEntity() }
        return BossTalkPostsResponseEntity(
            postList=postEntities,
            hasNext = hasNext
        )
    }

}

data class PostDto(
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
    @SerializedName("like")
    val like: Boolean,
    @SerializedName("bookmark")
    val bookmark: Boolean,
    @SerializedName("createdAt")
    val createdAt: String, //LocalDate인데가 string으로 해야 에러가 안뜸,,
){
    fun toPostEntity()=PostEntity(
        postId=postId,
        title=title,
        content=content,
        bookmarkCount=bookmarkCount,
        commentCount=commentCount,
        likeCount=likeCount,
        like=like,
        bookmark=bookmark,
        createdAt=createdAt
    )

}
