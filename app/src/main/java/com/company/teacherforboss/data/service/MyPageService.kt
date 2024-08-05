package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.response.auth.AccountResponseDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPageAnsweredQuestionDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MyPageService {
    companion object{
        const val MYPAGE="mypage"
    }
    @GET("${MYPAGE}/board/answered-questions?lastPostId={lastPostId}&size={size}")
    suspend fun getAnsweredQuestion(
        @Query("lastQuestionId") lastQuestionId:Long,
        @Query("size") size:Int,
    )
    : BaseResponse<ResponseMyPageAnsweredQuestionDto>
}