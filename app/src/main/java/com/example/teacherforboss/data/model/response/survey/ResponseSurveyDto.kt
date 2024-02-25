package com.example.teacherforboss.data.model.response.survey

import com.example.teacherforboss.domain.model.SurveyResultEntity
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSurveyDto(
    @SerialName("surveyId")
    val surveyId: Long,
    @SerialName("createdAt")
    val createdAt: LocalDateTime,
) {
    fun toSurveyResultEntity() = SurveyResultEntity(
        surveyId = surveyId,
        createdAt = createdAt.toString()
    )
}
