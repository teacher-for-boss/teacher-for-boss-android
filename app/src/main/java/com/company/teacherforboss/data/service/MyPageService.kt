package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.response.auth.AccountResponseDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPageAnsweredQuestionDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPagePostsDto

interface MyPageService {
    companion object{
        const val MYPAGE="mypage"
    }
    @GET("${MYPAGE}/board/answered-questions")
    suspend fun getAnsweredQuestion(
        @Query("lastQuestionId") lastQuestionId:Long,
        @Query("size") size:Int,
    )
    : BaseResponse<ResponseMyPageAnsweredQuestionDto>
    @GET("${MYPAGE}/board/my-questions")
    suspend fun getMyQuestion(
        @Query("lastQuestionId") lastQuestionId:Long,
        @Query("size") size:Int,
    )
    : BaseResponse<ResponseMyPageAnsweredQuestionDto>

    @GET("mypage/board/bookmarked-questions")
    suspend fun getBookmarkedQuestions(
    ): BaseResponse<ResponseBookmarkedQuestionsDto>

    @GET("${MYPAGE}/board/commented-posts")
    suspend fun getCommentedPosts(
        @Query("lastPostId") lastPostId:Long,
        @Query("size") size:Int,
    )
    : BaseResponse<ResponseMyPagePostsDto>

    @GET("${MYPAGE}/board/my-posts")
    suspend fun getMyPosts(
        @Query("lastPostId") lastPostId:Long,
        @Query("size") size:Int,
    )
    : BaseResponse<ResponseMyPagePostsDto>

}