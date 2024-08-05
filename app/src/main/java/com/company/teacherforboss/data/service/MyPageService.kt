package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.response.mypage.ResponseAnsweredQuestionDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET

interface MyPageService {
    @GET("mypage/board/bookmarked-questions")
    suspend fun getAnsweredQuestion(
    ): BaseResponse<ResponseAnsweredQuestionDto>
}