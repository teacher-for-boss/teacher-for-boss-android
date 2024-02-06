package com.example.teacherforboss.data.mapper

import com.example.teacherforboss.data.model.request.RequestSurveyDto
import com.example.teacherforboss.domain.model.SurveyEntity

fun SurveyEntity.toRequestSurveyDto() = RequestSurveyDto(
    question1 = this.question1,
    question2 = this.question2,
    question3 = this.question3,
    question4 = this.question4
)