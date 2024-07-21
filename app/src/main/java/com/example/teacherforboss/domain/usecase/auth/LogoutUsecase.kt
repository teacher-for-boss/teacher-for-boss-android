package com.example.teacherforboss.domain.usecase.auth

import com.example.teacherforboss.domain.model.auth.LogoutResponseEntity
import com.example.teacherforboss.domain.repository.AuthRepository

class LogoutUsecase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke():Result<LogoutResponseEntity> = authRepository.logout()

}