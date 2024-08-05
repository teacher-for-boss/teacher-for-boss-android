package com.company.teacherforboss.data.repository

import com.company.teacherforboss.data.datasource.remote.MemberRemoteDataSource
import com.company.teacherforboss.domain.model.auth.AccountEntity
import com.company.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.company.teacherforboss.domain.repository.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberRemoteDataSource: MemberRemoteDataSource
):MemberRepository {
    override suspend fun getAccount(): Result<AccountEntity> =
        runCatching { memberRemoteDataSource.getAccount().result.toAccountEntity() }

    override suspend fun getProfile(): Result<MyPageProfileEntity> =
        runCatching { memberRemoteDataSource.getProfile().result.toMyPageProfileEntity() }


}