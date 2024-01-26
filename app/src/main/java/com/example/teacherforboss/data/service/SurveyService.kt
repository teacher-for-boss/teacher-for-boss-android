package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.model.request.RequestSurveyDto
import com.example.teacherforboss.data.model.response.ResponseSurveyDto
import retrofit2.http.Body
import retrofit2.http.POST

interface SurveyService {
    @POST("signup/surveyForClass")
    suspend fun postSurveyResult(
        @Body request: RequestSurveyDto
    ): ResponseSurveyDto
}