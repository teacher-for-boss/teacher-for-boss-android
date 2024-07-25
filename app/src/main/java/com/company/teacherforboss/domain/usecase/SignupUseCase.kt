package com.company.teacherforboss.domain.usecase

import com.company.teacherforboss.domain.model.signup.SignupEntity
import com.company.teacherforboss.domain.model.signup.SignupResultEntity
import com.company.teacherforboss.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow

class SignupUseCase(
    private val signupRepository: SignupRepository
) {
    suspend operator fun invoke(signupEntity: SignupEntity): Flow<SignupResultEntity> = signupRepository.signup(signupEntity)

}