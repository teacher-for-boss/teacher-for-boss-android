package com.example.teacherforboss.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSurveyDto(
    @SerialName("surveyId")
    val surveyId: Long
)
