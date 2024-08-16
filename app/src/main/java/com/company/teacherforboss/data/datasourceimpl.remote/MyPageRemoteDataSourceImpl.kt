package com.company.teacherforboss.data.datasourceimpl.remote

import com.company.teacherforboss.data.datasource.remote.MyPageRemoteDataSource
import com.company.teacherforboss.data.model.request.mypage.RequestBookmarkedQuestionsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto
import com.company.teacherforboss.data.service.MyPageService
import com.company.teacherforboss.util.base.BaseResponse

import javax.inject.Inject

class MyPageRemoteDataSourceImpl @Inject constructor(
    private val myPageService: MyPageService
) :MyPageRemoteDataSource{
    override suspend fun getBookmarkedQuestions(requestBookmarkedQuestionsDto: RequestBookmarkedQuestionsDto): BaseResponse<ResponseBookmarkedQuestionsDto>
    =myPageService.getBookmarkedQuestions(lastQuestionId = requestBookmarkedQuestionsDto.lastQuestionId, size = requestBookmarkedQuestionsDto.size)
}