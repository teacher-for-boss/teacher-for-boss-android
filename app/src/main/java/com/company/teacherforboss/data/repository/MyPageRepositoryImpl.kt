package com.company.teacherforboss.data.repository

import com.company.teacherforboss.data.datasource.remote.MyPageRemoteDataSource
import com.company.teacherforboss.domain.model.mypage.AnsweredQuestionResponseEntity
import com.company.teacherforboss.domain.repository.MyPageRepository
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myPageDataSource: MyPageRemoteDataSource
) :MyPageRepository {
    override suspend fun getAnsweredQuestion(): Result<AnsweredQuestionResponseEntity> =
        runCatching {
            myPageDataSource.getAnsweredQuestion().result.toAnsweredQuestionEntity()
        }
}