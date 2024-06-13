package com.example.teacherforboss.data.service

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


    companion object {
        const val BOSS = "board/boss"
    }
}