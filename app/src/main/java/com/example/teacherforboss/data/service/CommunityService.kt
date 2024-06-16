package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkPostsDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CommunityService {
    @GET("${BOSS}/posts?")
    suspend fun getBossTalkPosts(
        @Query("sortBy") sortBy:String,
        @Query("lastPostId") lastPostId:Long,
        @Query("size") size:Int

    ):BaseResponse<ResponseBossTalkPostsDto>

    @GET("${BOSS}/search?")
    suspend fun searchKeywordBossTalk(
        @Query("keyword") keyword:String,
        @Query("lastPostId") lastPostId:Long,
        @Query("size") size:Int

    ):BaseResponse<ResponseBossTalkPostsDto>



    companion object {
        const val BOSS = "board/boss"
    }
}