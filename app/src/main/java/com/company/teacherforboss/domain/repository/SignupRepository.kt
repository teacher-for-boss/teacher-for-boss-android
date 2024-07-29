package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.signup.SignupEntity
import com.company.teacherforboss.domain.model.signup.SignupResultEntity
import kotlinx.coroutines.flow.Flow

interface SignupRepository {
    suspend fun signup(signupEntity: SignupEntity): Flow<SignupResultEntity>

}