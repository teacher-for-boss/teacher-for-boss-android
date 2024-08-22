package com.company.teacherforboss.data.repositoryImpl

import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity
import com.company.teacherforboss.data.datasource.remote.MyPageRemoteDataSource
import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsRequestEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsResponseEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsRequestEntity
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsResponseEntity
import com.company.teacherforboss.domain.repository.MyPageRepository
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myPageDataSource: MyPageRemoteDataSource
) :MyPageRepository {

    override suspend fun getBookmarkedQuestions(bookmarkedQuestionsRequestEntity: BookmarkedQuestionsRequestEntity): BookmarkedQuestionsResponseEntity =
        runCatching {
            myPageDataSource.getBookmarkedQuestions(
                requestBookmarkedQuestionsDto = bookmarkedQuestionsRequestEntity.toRequestBookmarkedQuestionsDto()
            ).result.toBookmarkedQuestionsEntity()
        }.getOrElse { err -> throw err }

    override suspend fun getBookmarkedPosts(bookmarkedPostsRequestEntity: BookmarkedPostsRequestEntity): BookmarkedPostsResponseEntity =
        runCatching {
            myPageDataSource.getBookmarkedPosts(
                requestBookmarkedPostsDto = bookmarkedPostsRequestEntity.toRequestBookmarkedPostsDto()
            ).result.toBookmarkedPostsEntity()
        }.getOrElse { err -> throw err }

    override suspend fun getAnsweredQuestion(myPageAnsweredQuestionRequestEntity: MyPageAnsweredQuestionRequestEntity): MyPageAnsweredQuestionResponseEntity =
        runCatching {
            myPageDataSource.getAnsweredQuestion(
                requestMyPageAnsweredQuestionDto = myPageAnsweredQuestionRequestEntity.toRequestMyPageAnsweredQuestionsDto()
            ).result.toMyPageAnsweredQuestionResponseEntity()
        }.getOrElse { err -> throw err }

}