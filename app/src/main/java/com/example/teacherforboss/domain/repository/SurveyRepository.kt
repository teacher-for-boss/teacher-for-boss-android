package com.example.teacherforboss.domain.repository

interface SurveyRepository {
    suspend fun postSurveyResult(): Unit?
}