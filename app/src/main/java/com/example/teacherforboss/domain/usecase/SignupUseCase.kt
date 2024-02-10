package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.SignupEntity
import com.example.teacherforboss.domain.model.SignupResultEntity
import com.example.teacherforboss.domain.repository.SignupRepository
import com.example.teacherforboss.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class SignupUseCase(
    private val signupRepository: SignupRepository
) {
    suspend operator fun invoke(signupEntity: SignupEntity): Flow<SignupResultEntity> = signupRepository.signup(signupEntity)

}