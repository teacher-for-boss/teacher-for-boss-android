package com.company.teacherforboss.domain.usecase.auth

import com.company.teacherforboss.domain.model.auth.LogoutResponseEntity
import com.company.teacherforboss.domain.repository.AuthRepository

class LogoutUsecase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(fcmToken:String):Result<LogoutResponseEntity> = authRepository.logout(fcmToken)

}