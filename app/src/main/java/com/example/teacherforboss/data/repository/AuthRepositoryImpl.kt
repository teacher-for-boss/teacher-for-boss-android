package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.datasource.remote.AuthRemoteDataSource
import com.example.teacherforboss.domain.model.auth.LogoutResponseEntity
import com.example.teacherforboss.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
):AuthRepository {
    override suspend fun logout(): Result<LogoutResponseEntity> =
        runCatching {
            authRemoteDataSource.logout().result.toLogoutResponseEntity()
        }
    }