package com.example.teacherforboss.data.model.request.exam

import com.example.teacherforboss.domain.model.ExamResultEntity
import com.example.teacherforboss.domain.model.ExamResultResultEntity
import com.google.gson.annotations.SerializedName

data class RequestExamResultDto(
    @SerializedName("examId")
    val examId:Int
)
