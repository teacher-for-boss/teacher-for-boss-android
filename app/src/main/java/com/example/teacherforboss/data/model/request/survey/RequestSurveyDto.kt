package com.example.teacherforboss.data.model.request.survey

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSurveyDto(
    @SerialName("question1")
    val question1: Int,
    @SerialName("question2")
    val question2: List<Int>,
    @SerialName("question3")
    val question3: Int,
    @SerialName("question4")
    val question4: String
)
