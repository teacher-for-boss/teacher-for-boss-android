package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedPostsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto
import com.company.teacherforboss.data.model.response.auth.AccountResponseDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPageAnsweredQuestionDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Query
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto

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

    @GET("mypage/board/bookmarked-questions")
    suspend fun getBookmarkedQuestions(
        @Query("lastQuestionId") lastQuestionId:Long,
        @Query("size") size:Int,
    ): BaseResponse<ResponseBookmarkedQuestionsDto>

    @GET("mypage/board/bookmarked-posts")
    suspend fun getBookmarkedPosts(
        @Query("lastPostId") lastPostId:Long,
        @Query("size") size:Int,
    ): BaseResponse<ResponseBookmarkedPostsDto>

}