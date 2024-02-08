package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.SignupEntity
import com.example.teacherforboss.domain.model.SignupResultEntity
import com.example.teacherforboss.domain.model.SurveyResultEntity
import kotlinx.coroutines.flow.Flow

interface SignupRepository {
    suspend fun signup(signupEntity: SignupEntity): Flow<SignupResultEntity>

}