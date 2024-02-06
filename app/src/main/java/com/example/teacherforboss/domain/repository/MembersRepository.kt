package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.SurveyEntity
import com.example.teacherforboss.domain.model.SurveyResultEntity
import kotlinx.coroutines.flow.Flow

interface MembersRepository {
    suspend fun postSurveyResult(surveyEntity: SurveyEntity): Flow<SurveyResultEntity>
}
