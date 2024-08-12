package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.response.auth.AccountResponseDto
import com.company.teacherforboss.data.model.response.mypage.RequestMyPageAnsweredQuestionDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPageAnsweredQuestionDto
import com.company.teacherforboss.util.base.BaseResponse
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseChipInfoDto

interface MyPageRemoteDataSource {
    suspend fun getAnsweredQuestion(requestMyPageAnsweredQuestionDto: RequestMyPageAnsweredQuestionDto): BaseResponse<ResponseMyPageAnsweredQuestionDto>
    suspend fun getBookmarkedQuestions(): BaseResponse<ResponseBookmarkedQuestionsDto>
    suspend fun getChipInfo(): BaseResponse<ResponseChipInfoDto>
}