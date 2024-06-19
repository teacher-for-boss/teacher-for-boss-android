package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBodyDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBookmarkDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkLikeDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkPostsDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CommunityService {
    @GET("${BOSS}/posts?")
    suspend fun getBossTalkPosts(
        @Query("lastPostId") lastPostId:Long,
        @Query("size") size:Int,
        @Query("sortBy") sortBy:String

        ):BaseResponse<ResponseBossTalkPostsDto>

    @GET("${BOSS}/search?")
    suspend fun searchKeywordBossTalk(
        @Query("keyword") keyword:String,
        @Query("lastPostId") lastPostId:Long,
        @Query("size") size:Int

    ):BaseResponse<ResponseBossTalkPostsDto>

    @GET("${BOSS}/posts/{postId}/bookmark")
    suspend fun getBossTalkBookmark(
        @Query("postId") postId:Long

    ):BaseResponse<ResponseBossTalkBookmarkDto>

    @GET("${BOSS}/posts/{postId}/likes")
    suspend fun getBossTalkLike(
        @Query("postId") postId:Long

    ):BaseResponse<ResponseBossTalkLikeDto>

    @GET("${BOSS}/posts/{postId}")
    suspend fun getBossTalkBody(
        @Query("postId") postId:Long

    ):BaseResponse<ResponseBossTalkBodyDto>



    companion object {
        const val BOSS = "board/boss"
    }
}