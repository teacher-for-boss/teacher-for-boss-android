package com.company.teacherforboss.data.repository

import com.company.teacherforboss.data.datasource.remote.MemberRemoteDataSource
import com.company.teacherforboss.data.datasource.remote.MyPageRemoteDataSource
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeRequestEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeResponseEntity
import com.company.teacherforboss.domain.repository.MemberRepository
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

}