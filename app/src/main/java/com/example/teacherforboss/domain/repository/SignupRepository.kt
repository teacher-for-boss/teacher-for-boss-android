package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.signup.SignupEntity
import com.example.teacherforboss.domain.model.signup.SignupResultEntity
import kotlinx.coroutines.flow.Flow

interface SignupRepository {
    suspend fun signup(signupEntity: SignupEntity): Flow<SignupResultEntity>

}