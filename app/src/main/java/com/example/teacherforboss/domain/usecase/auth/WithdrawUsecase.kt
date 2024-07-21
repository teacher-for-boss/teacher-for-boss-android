package com.example.teacherforboss.domain.usecase.auth

import com.example.teacherforboss.domain.model.auth.WithdrawResponseEntity
import com.example.teacherforboss.domain.repository.AuthRepository

class WithdrawUsecase (
    private val authRepository: AuthRepository
    ) {
    suspend operator fun invoke():Result<WithdrawResponseEntity> = authRepository.withdraw()
}