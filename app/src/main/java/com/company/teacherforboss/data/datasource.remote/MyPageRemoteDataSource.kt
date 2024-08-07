package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto
import com.company.teacherforboss.util.base.BaseResponse

interface MyPageRemoteDataSource {
    suspend fun getBookmarkedQuestions(): BaseResponse<ResponseBookmarkedQuestionsDto>
}