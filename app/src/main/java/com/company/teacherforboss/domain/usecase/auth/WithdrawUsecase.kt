package com.company.teacherforboss.domain.usecase.auth

import com.company.teacherforboss.domain.model.auth.WithdrawResponseEntity
import com.company.teacherforboss.domain.repository.AuthRepository

class WithdrawUsecase (
    private val authRepository: AuthRepository
    ) {
    suspend operator fun invoke():Result<WithdrawResponseEntity> = authRepository.withdraw()
}