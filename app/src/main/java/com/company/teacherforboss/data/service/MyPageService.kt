package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedPostsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MyPageService {
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