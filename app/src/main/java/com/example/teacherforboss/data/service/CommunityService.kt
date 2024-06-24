package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossUploadPostDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBodyDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBookmarkDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkLikeDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkPostsDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossUploadPostDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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

    @POST("${BOSS}/posts")
    suspend fun uploadPost(
        @Body requestBossUploadPostDto: RequestBossUploadPostDto
    ):BaseResponse<ResponseBossUploadPostDto>

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
        @Path("postId") postId:Long

    ):BaseResponse<ResponseBossTalkBodyDto>
    @POST("${BOSS}/posts/{postId}/comments")
    suspend fun postBossTalkComment(
        @Body requestBossTalkCommentDto: RequestBossTalkCommentDto,
        @Path("postId") postId:Long

    ):BaseResponse<ResponseBossTalkCommentDto>


    companion object {
        const val BOSS = "board/boss"
    }
}