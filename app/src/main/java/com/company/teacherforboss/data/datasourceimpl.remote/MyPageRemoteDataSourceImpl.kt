package com.company.teacherforboss.data.datasourceimpl.remote

import com.company.teacherforboss.data.datasource.remote.MyPageRemoteDataSource
import com.company.teacherforboss.data.model.request.mypage.RequestMyPageAnsweredQuestionDto
import com.company.teacherforboss.data.model.request.mypage.RequestMyPageCommentedPostsDto
import com.company.teacherforboss.data.model.request.mypage.RequestMyPageMyPostsDto
import com.company.teacherforboss.data.model.request.mypage.RequestMyPageMyQuestionDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPageAnsweredQuestionDto
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPagePostsDto
import com.company.teacherforboss.data.service.MyPageService
import com.company.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class MyPageRemoteDataSourceImpl @Inject constructor(
    private val myPageService: MyPageService,
) : MyPageRemoteDataSource {
  
    override suspend fun getAnsweredQuestion(requestMyPageAnsweredQuestionDto: RequestMyPageAnsweredQuestionDto): BaseResponse<ResponseMyPageAnsweredQuestionDto>
    =myPageService.getAnsweredQuestion(lastQuestionId = requestMyPageAnsweredQuestionDto.lastQuestionId, size = requestMyPageAnsweredQuestionDto.size)

    override suspend fun getMyQuestion(requestMyPageMyQuestionDto: RequestMyPageMyQuestionDto): BaseResponse<ResponseMyPageAnsweredQuestionDto>
    =myPageService.getMyQuestion(lastQuestionId = requestMyPageMyQuestionDto.lastQuestionId, size = requestMyPageMyQuestionDto.size)

    override suspend fun getBookmarkedQuestions(): BaseResponse<ResponseBookmarkedQuestionsDto>
    =myPageService.getBookmarkedQuestions()

    override suspend fun getCommentedPosts(requestMyPageCommentedPostsDto: RequestMyPageCommentedPostsDto): BaseResponse<ResponseMyPagePostsDto>
            =myPageService.getCommentedPosts(lastPostId = requestMyPageCommentedPostsDto.lastPostId, size = requestMyPageCommentedPostsDto.size)

    override suspend fun getMyPosts(requestMyPageMyPostsDto: RequestMyPageMyPostsDto): BaseResponse<ResponseMyPagePostsDto>
            =myPageService.getMyPosts(lastPostId = requestMyPageMyPostsDto.lastPostId, size = requestMyPageMyPostsDto.size)

}