package com.example.teacherforboss.data.service

import androidx.compose.ui.text.TextRange
import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossUploadPostDto
import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherAnswerPostDto
import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherUploadPostDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossCommentDeleteDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossModifyDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBodyDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBookmarkDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentListDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkDeletePostDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentLikeDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkLikeDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkPostsDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossUploadPostDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseTeacherTalkAnsDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherAnswerListDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherAnswerModifyDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherAnswerPostDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherDeleteDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherModifyDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherSelectDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkAnswerLikeDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkBodyDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkBookmarkDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkLikeDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherUploadPostDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkQuestionsDto
import com.example.teacherforboss.data.service.CommunityService.Companion.TEACHER
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @GET("${TEACHER}/questions?")
    suspend fun getTeacherTalkQuestions(
        @Query("category") category:String,
        @Query("lastQuestionId") lastQuestionId:Long,
        @Query("size") size:Int,
        @Query("sortBy") sortBy:String,

    ):BaseResponse<ResponseTeacherTalkQuestionsDto>

    @GET("${BOSS}/posts/search?")
    suspend fun searchKeywordBossTalk(
        @Query("keyword") keyword:String,
        @Query("sortBy") sortBy: String,
        @Query("lastPostId") lastPostId:Long,
        @Query("size") size:Int,

    ):BaseResponse<ResponseBossTalkPostsDto>

    @GET("${TEACHER}/questions/search?")
    suspend fun searchKeywordTeacherTalk(
        @Query("keyword") keyword: String,
        @Query("lastQuestionId") lastQuestionId: Long,
        @Query("size") size: Int
    ): BaseResponse<ResponseTeacherTalkQuestionsDto>

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

    @POST("${BOSS}/posts/{postId}/comments/{commentId}/likes")
    suspend fun likeBossTalkComment(
        @Path("postId") postId:Long,
        @Path("commentId") commentId:Long,
    ):BaseResponse<ResponseBossTalkCommentLikeDto>

    @POST("${BOSS}/posts/{postId}/comments/{commentId}/dislikes")
    suspend fun dislikeBossTalkComment(
        @Path("postId") postId:Long,
        @Path("commentId") commentId:Long,
    ):BaseResponse<ResponseBossTalkCommentLikeDto>

    @DELETE("${BOSS}/posts/{postId}/comments/{commentId}")
    suspend fun deleteBossTalkComment(
        @Path("postId") postId: Long,
        @Path("commentId") commentId: Long
    ): BaseResponse<ResponseBossCommentDeleteDto>

    @POST("${TEACHER}/questions")
    suspend fun uploadPostTeacher(
        @Body requestTeacherUploadPostDto: RequestTeacherUploadPostDto
    ): BaseResponse<ResponseTeacherUploadPostDto>

    @PATCH("${TEACHER}/questions/{questionId}")
    suspend fun modifyTeacherTalkBody(
        @Path("questionId") questionId: Long,
        @Body requestTeacherUploadPostDto: RequestTeacherUploadPostDto
    ): BaseResponse<ResponseTeacherModifyDto>

    @DELETE("${TEACHER}/questions/{questionId}")
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

    @DELETE("${BOSS}/posts/{postId}")
    suspend fun deleteBossTalkPost(
        @Path("postId") postId:Long
    ):BaseResponse<ResponseBossTalkDeletePostDto>

   @PATCH("${TEACHER}/questions/{questionId}/answers/{answerId}")
    suspend fun modifyTeacherTalkAnswer(
        @Path("questionId") questionId: Long,
        @Path("answerId") answerId: Long,
        @Body requestTeacherAnswerPostDto: RequestTeacherAnswerPostDto
    ): BaseResponse<ResponseTeacherAnswerModifyDto>

    @PATCH("${TEACHER}/questions/{questionId}/answers/{answerId}/select")
    suspend fun selectTeacherTalkAnswer(
        @Path("questionId") questionId: Long,
        @Path("answerId") answerId: Long
    ): BaseResponse<ResponseTeacherSelectDto>


    @POST("${TEACHER}/questions/{questionId}/likes")
    suspend fun getTeacherTalkLike(
        @Path("questionId") questionId:Long
    ):BaseResponse<ResponseTeacherTalkLikeDto>

    @POST("${TEACHER}/questions/{questionId}/bookmark")
    suspend fun getTeacherTalkBookmark(
        @Path("questionId") questionId:Long
    ):BaseResponse<ResponseTeacherTalkBookmarkDto>

    @GET("${TEACHER}/questions/{questionId}")
    suspend fun getTeacherTalkBody(
        @Path("questionId") questionId:Long
    ):BaseResponse<ResponseTeacherTalkBodyDto>

    @DELETE("${TEACHER}/questions/{questionId}/answers/{answerId}")
    suspend fun deleteTeacherTalkAns(
        @Path("questionId") questionId:Long,
        @Path("answerId") answerId:Long?
    ):BaseResponse<ResponseTeacherTalkAnsDto>

    @POST("${TEACHER}/questions/{questionId}/answers/{answerId}/likes")
    suspend fun likeTeacherTalkAnswer(
        @Path("questionId") questionId:Long,
        @Path("answerId") answerId:Long,
    ):BaseResponse<ResponseTeacherTalkAnswerLikeDto>

    @POST("${TEACHER}/questions/{questionId}/answers/{answerId}/dislikes")
    suspend fun dislikeTeacherTalkAnswer(
        @Path("questionId") questionId:Long,
        @Path("answerId") answerId:Long,
    ):BaseResponse<ResponseTeacherTalkAnswerLikeDto>

    companion object {
        const val BOSS = "board/boss"
        const val TEACHER = "board/teacher"
    }
}