package com.company.teacherforboss.data.repositoryImpl

import com.company.teacherforboss.data.datasource.remote.AuthRemoteDataSource
import com.company.teacherforboss.domain.model.auth.LogoutResponseEntity
import com.company.teacherforboss.domain.model.auth.WithdrawResponseEntity
import com.company.teacherforboss.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
):AuthRepository {
    override suspend fun logout(): Result<LogoutResponseEntity> =
        runCatching { authRemoteDataSource.logout().result.toLogoutResponseEntity() }

    override suspend fun withdraw(): Result<WithdrawResponseEntity> =
        runCatching { authRemoteDataSource.withdraw().result.toWithdrawEntity() }
}