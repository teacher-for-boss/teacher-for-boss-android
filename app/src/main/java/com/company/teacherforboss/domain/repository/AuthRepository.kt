package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.auth.LogoutResponseEntity
import com.company.teacherforboss.domain.model.auth.WithdrawResponseEntity

interface AuthRepository {

    suspend fun logout(fcmToken:String):Result<LogoutResponseEntity>

    suspend fun withdraw():Result<WithdrawResponseEntity>
}