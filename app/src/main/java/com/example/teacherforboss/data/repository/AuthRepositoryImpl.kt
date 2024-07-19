package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.datasource.remote.AuthRemoteDataSource
import com.example.teacherforboss.domain.model.auth.AccountEntity
import com.example.teacherforboss.domain.model.auth.LogoutResponseEntity
import com.example.teacherforboss.domain.model.auth.WithdrawResponseEntity
import com.example.teacherforboss.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
):AuthRepository {
    override suspend fun getAccount(): Result<AccountEntity> =
        runCatching { authRemoteDataSource.getAccount().result.toAccountEntity() }

    override suspend fun logout(): Result<LogoutResponseEntity> =
        runCatching { authRemoteDataSource.logout().result.toLogoutResponseEntity() }

    override suspend fun withdraw(): Result<WithdrawResponseEntity> =
        runCatching { authRemoteDataSource.withdraw().result.toWithdrawEntity() }
}