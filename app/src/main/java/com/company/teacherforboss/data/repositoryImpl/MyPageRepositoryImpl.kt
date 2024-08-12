package com.company.teacherforboss.data.repositoryImpl

import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity
import com.company.teacherforboss.data.datasource.remote.MyPageRemoteDataSource
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsResponseEntity
import com.company.teacherforboss.domain.model.mypage.ChipInfoResponseEntity
import com.company.teacherforboss.domain.repository.MyPageRepository
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myPageRemoteDataSource: MyPageRemoteDataSource
):  MyPageRepository {
    override suspend fun getAnsweredQuestion(myPageAnsweredQuestionRequestEntity: MyPageAnsweredQuestionRequestEntity): MyPageAnsweredQuestionResponseEntity =
        runCatching{
            myPageRemoteDataSource.getAnsweredQuestion(myPageAnsweredQuestionRequestEntity.toRequestMyPageAnsweredQuestionsDto())
                .result.toMyPageAnsweredQuestionResponseEntity()
        }.getOrElse { err -> throw err }
        
    override suspend fun getBookmarkedQuestions(): BookmarkedQuestionsResponseEntity =
        runCatching {
            myPageRemoteDataSource.getBookmarkedQuestions().result.toBookmarkedQuestionsEntity()
        }.getOrElse { err -> throw err }

    override suspend fun getChipInfo(): Result<ChipInfoResponseEntity> =
        runCatching {
            myPageRemoteDataSource.getChipInfo().result.toChipInfoResponseEntity()
        }
}