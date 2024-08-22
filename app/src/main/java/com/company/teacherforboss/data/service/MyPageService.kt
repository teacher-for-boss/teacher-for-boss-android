package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.response.auth.AccountResponseDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPageAnsweredQuestionDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseChipInfoDto

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

    @GET("${MYPAGE}/board/bookmarked-questions")
    suspend fun getBookmarkedQuestions(
    ): BaseResponse<ResponseBookmarkedQuestionsDto>

    @GET("${MYPAGE}/board/info")
    suspend fun getChipInfo(
    ): BaseResponse<ResponseChipInfoDto>

}