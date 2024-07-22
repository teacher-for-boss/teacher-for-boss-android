package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.auth.AccountEntity
import com.example.teacherforboss.domain.model.auth.LogoutResponseEntity
import com.example.teacherforboss.domain.model.auth.WithdrawResponseEntity

interface AuthRepository {

    suspend fun logout():Result<LogoutResponseEntity>

    suspend fun withdraw():Result<WithdrawResponseEntity>
}