package com.example.teacherforboss.domain.usecase.auth

import com.example.teacherforboss.domain.model.auth.AccountEntity
import com.example.teacherforboss.domain.repository.AuthRepository
import javax.inject.Inject

class AccountUsecase @Inject constructor(
    val authRepository: AuthRepository
) {
    suspend operator fun invoke():Result<AccountEntity> = authRepository.getAccount()
}