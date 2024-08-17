package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.request.mypage.RequestMyPageAnsweredQuestionDto
import com.company.teacherforboss.data.model.request.mypage.RequestMyPageCommentedPostsDto
import com.company.teacherforboss.data.model.request.mypage.RequestMyPageMyPostsDto
import com.company.teacherforboss.data.model.request.mypage.RequestMyPageMyQuestionDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPageAnsweredQuestionDto
import com.company.teacherforboss.util.base.BaseResponse
import com.company.teacherforboss.data.model.response.mypage.ResponseBookmarkedQuestionsDto
import com.company.teacherforboss.data.model.response.mypage.ResponseMyPagePostsDto

interface MyPageRemoteDataSource {
    suspend fun getAnsweredQuestion(requestMyPageAnsweredQuestionDto: RequestMyPageAnsweredQuestionDto): BaseResponse<ResponseMyPageAnsweredQuestionDto>

    suspend fun getMyQuestion(requestMyPageMyQuestionDto: RequestMyPageMyQuestionDto): BaseResponse<ResponseMyPageAnsweredQuestionDto>

    suspend fun getBookmarkedQuestions(): BaseResponse<ResponseBookmarkedQuestionsDto>

    suspend fun getCommentedPosts(requestMyPageCommentedPostsDto: RequestMyPageCommentedPostsDto):BaseResponse<ResponseMyPagePostsDto>

    suspend fun getMyPosts(requestMyPageMyPostsDto: RequestMyPageMyPostsDto):BaseResponse<ResponseMyPagePostsDto>

}