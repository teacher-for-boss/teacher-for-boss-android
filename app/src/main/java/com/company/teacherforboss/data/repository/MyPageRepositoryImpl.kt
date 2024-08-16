package com.company.teacherforboss.data.repository

import com.company.teacherforboss.data.datasource.remote.MyPageRemoteDataSource
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
}