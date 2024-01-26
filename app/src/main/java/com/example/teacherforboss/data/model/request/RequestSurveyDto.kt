package com.example.teacherforboss.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSurveyDto(
    @SerialName("question1")
    val question1: Long
)
