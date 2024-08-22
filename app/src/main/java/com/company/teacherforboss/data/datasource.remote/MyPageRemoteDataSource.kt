package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.request.mypage.RequestBookmarkedPostsDto
import com.company.teacherforboss.data.model.request.mypage.RequestBookmarkedQuestionsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedPostsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto
import com.company.teacherforboss.data.model.response.auth.AccountResponseDto
import com.company.teacherforboss.data.model.response.mypage.RequestMyPageAnsweredQuestionDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPageAnsweredQuestionDto
import com.company.teacherforboss.util.base.BaseResponse
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto

interface MyPageRemoteDataSource {
    suspend fun getBookmarkedQuestions(requestBookmarkedQuestionsDto: RequestBookmarkedQuestionsDto): BaseResponse<ResponseBookmarkedQuestionsDto>
    suspend fun getBookmarkedPosts(requestBookmarkedPostsDto: RequestBookmarkedPostsDto): BaseResponse<ResponseBookmarkedPostsDto>
    suspend fun getAnsweredQuestion(requestMyPageAnsweredQuestionDto: RequestMyPageAnsweredQuestionDto): BaseResponse<ResponseMyPageAnsweredQuestionDto>

    suspend fun getBookmarkedQuestions(): BaseResponse<ResponseBookmarkedQuestionsDto>

}