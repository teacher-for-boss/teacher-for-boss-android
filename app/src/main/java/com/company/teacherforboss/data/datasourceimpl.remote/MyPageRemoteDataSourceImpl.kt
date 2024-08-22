package com.company.teacherforboss.data.datasourceimpl.remote

import com.company.teacherforboss.data.datasource.remote.MyPageRemoteDataSource
import com.company.teacherforboss.data.model.request.mypage.RequestBookmarkedPostsDto
import com.company.teacherforboss.data.model.request.mypage.RequestBookmarkedQuestionsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedPostsDto
import com.company.teacherforboss.data.model.response.auth.AccountResponseDto
import com.company.teacherforboss.data.model.response.mypage.RequestMyPageAnsweredQuestionDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPageAnsweredQuestionDto
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto
import com.company.teacherforboss.data.service.MyPageService
import com.company.teacherforboss.util.base.BaseResponse

import javax.inject.Inject

class MyPageRemoteDataSourceImpl @Inject constructor(
    private val myPageService: MyPageService
) :MyPageRemoteDataSource{
    override suspend fun getBookmarkedQuestions(requestBookmarkedQuestionsDto: RequestBookmarkedQuestionsDto): BaseResponse<ResponseBookmarkedQuestionsDto>
    =myPageService.getBookmarkedQuestions(lastQuestionId = requestBookmarkedQuestionsDto.lastQuestionId, size = requestBookmarkedQuestionsDto.size)

    override suspend fun getBookmarkedPosts(requestBookmarkedPostsDto: RequestBookmarkedPostsDto): BaseResponse<ResponseBookmarkedPostsDto>
    =myPageService.getBookmarkedPosts(lastPostId = requestBookmarkedPostsDto.lastPostId, size = requestBookmarkedPostsDto.size)
}