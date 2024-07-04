package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossUploadPostDto
import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherAnswerPostDto
import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherUploadPostDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossModifyDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBodyDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBookmarkDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentListDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkLikeDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkPostsDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossUploadPostDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherAnswerListDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherAnswerPostDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherDeleteDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherModifyDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkBodyDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkBookmarkDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkLikeDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherUploadPostDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
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
        @Body requestBossUploadPostDto: RequestBossUploadPostDto,
    ):BaseResponse<ResponseBossUploadPostDto>

    @POST("${BOSS}/posts/{postId}/bookmark")
    suspend fun getBossTalkBookmark(
        @Path("postId") postId:Long

    ):BaseResponse<ResponseBossTalkBookmarkDto>

    @POST("${BOSS}/posts/{postId}/likes")
    suspend fun getBossTalkLike(
        @Path("postId") postId:Long

    ):BaseResponse<ResponseBossTalkLikeDto>

    @GET("${BOSS}/posts/{postId}")
    suspend fun getBossTalkBody(
        @Path("postId") postId:Long

    ):BaseResponse<ResponseBossTalkBodyDto>

    @GET("${BOSS}/posts/{postId}/comments")
    suspend fun getBossTalkCommentList(
        @Path("postId") postId:Long

    ):BaseResponse<ResponseBossTalkCommentListDto>

    @POST("${BOSS}/posts/{postId}/comments")
    suspend fun postBossTalkComment(
        @Body requestBossTalkCommentDto: RequestBossTalkCommentDto,
        @Path("postId") postId:Long

    ):BaseResponse<ResponseBossTalkCommentDto>

    @PATCH("${BOSS}/posts/{postId}")
    suspend fun modifyBossTalkBody(
        @Path("postId") postId:Long,
        @Body requestBossUploadPostDto: RequestBossUploadPostDto,
    ):BaseResponse<ResponseBossModifyDto>

    @POST("${TEACHER}/questions")
    suspend fun uploadPostTeacher(
        @Body requestTeacherUploadPostDto: RequestTeacherUploadPostDto
    ): BaseResponse<ResponseTeacherUploadPostDto>

    @PATCH("${TEACHER}/questions/{questionId}")
    suspend fun modifyTeacherTalkBody(
        @Path("questionId") questionId: Long,
        @Body requestTeacherUploadPostDto: RequestTeacherUploadPostDto
    ): BaseResponse<ResponseTeacherModifyDto>

    @POST("${TEACHER}/questions/{questionId}")
    suspend fun deleteTeacherTalkBody(
        @Path("questionId") questionId: Long
    ): BaseResponse<ResponseTeacherDeleteDto>

    @GET("${TEACHER}/questions/{questionId}/answers")
    suspend fun getTeacherTalkAnswerList(
        @Path("questionId") questionId: Long
    ): BaseResponse<ResponseTeacherAnswerListDto>

    @POST("${TEACHER}/questions/{questionId}/answers")
    suspend fun postTeacherTalkAnswer(
        @Path("questionId") questionId: Long,
        @Body requestTeacherAnswerPostDto: RequestTeacherAnswerPostDto
    ): BaseResponse<ResponseTeacherAnswerPostDto>

    @POST("${TEACHER}questions/{questionId}/likes")
    suspend fun getTeacherTalkLike(
        @Path("questionId") questionId:Long
    ):BaseResponse<ResponseTeacherTalkLikeDto>

    @POST("${TEACHER}questions/{questionId}/bookmark")
    suspend fun getTeacherTalkBookmark(
        @Path("questionId") questionId:Long
    ):BaseResponse<ResponseTeacherTalkBookmarkDto>

    @GET("${TEACHER}/posts/{postId}")
    suspend fun getTeacherTalkBody(
        @Path("postId") questionId:Long

    ):BaseResponse<ResponseTeacherTalkBodyDto>

    companion object {
        const val BOSS = "board/boss"
        const val TEACHER = "board/teacher"
    }
}