package com.company.teacherforboss.data.repositoryImpl

import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity
import com.company.teacherforboss.data.datasource.remote.MyPageRemoteDataSource
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsResponseEntity
import com.company.teacherforboss.domain.model.mypage.MyPageCommentedPostsRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageMyPostsRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageMyQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPagePostsResponseEntity
import com.company.teacherforboss.domain.repository.MyPageRepository
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myPageRemoteDataSource: MyPageRemoteDataSource
):  MyPageRepository {
    override suspend fun getAnsweredQuestion(myPageAnsweredQuestionRequestEntity: MyPageAnsweredQuestionRequestEntity): MyPageAnsweredQuestionResponseEntity =
        runCatching{
            myPageRemoteDataSource.getAnsweredQuestion(myPageAnsweredQuestionRequestEntity.toRequestMyPageAnsweredQuestionDto())
                .result.toMyPageAnsweredQuestionResponseEntity()
        }.getOrElse { err -> throw err }

    override suspend fun getMyQuestion(myPageMyQuestionRequestEntity: MyPageMyQuestionRequestEntity): MyPageAnsweredQuestionResponseEntity =
        runCatching{
            myPageRemoteDataSource.getMyQuestion(myPageMyQuestionRequestEntity.toRequestMyPageMyQuestionDto())
                .result.toMyPageAnsweredQuestionResponseEntity()
        }.getOrElse { err -> throw err }
        
    override suspend fun getBookmarkedQuestions(): BookmarkedQuestionsResponseEntity =
        runCatching {
            myPageRemoteDataSource.getBookmarkedQuestions().result.toBookmarkedQuestionsEntity()
        }.getOrElse { err -> throw err }

    override suspend fun getCommentedPosts(myPageCommentedPostsRequestEntity: MyPageCommentedPostsRequestEntity): MyPagePostsResponseEntity =
        runCatching{
            myPageRemoteDataSource.getCommentedPosts(myPageCommentedPostsRequestEntity.toRequestMyPageCommentedPostsDto())
                .result.toMyPagePostsResponseEntity()
        }.getOrElse { err -> throw err }
    override suspend fun getMyPosts(myPageMyPostsRequestEntity: MyPageMyPostsRequestEntity): MyPagePostsResponseEntity =
        runCatching{
            myPageRemoteDataSource.getMyPosts(myPageMyPostsRequestEntity.toRequestMyPageMyPostsDto())
                .result.toMyPagePostsResponseEntity()
        }.getOrElse { err -> throw err }

}