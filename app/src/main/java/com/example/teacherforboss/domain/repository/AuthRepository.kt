package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.auth.LogoutResponseEntity

interface AuthRepository {
    suspend fun logout():Result<LogoutResponseEntity>
}