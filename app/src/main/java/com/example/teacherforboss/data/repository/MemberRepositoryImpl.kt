package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.datasource.remote.MemberRemoteDataSource
import com.example.teacherforboss.domain.model.auth.AccountEntity
import com.example.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.example.teacherforboss.domain.repository.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberRemoteDataSource: MemberRemoteDataSource
):MemberRepository {
    override suspend fun getAccount(): Result<AccountEntity> =
        runCatching { memberRemoteDataSource.getAccount().result.toAccountEntity() }

    override suspend fun getProfile(): MyPageProfileEntity {
        return runCatching { memberRemoteDataSource.getProfile().result.toMyPageProfileEntity()
        }.getOrElse { err -> throw err }
    }

}