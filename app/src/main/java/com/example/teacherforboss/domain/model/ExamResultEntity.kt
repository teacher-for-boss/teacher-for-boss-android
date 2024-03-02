package com.example.teacherforboss.domain.model

import com.example.teacherforboss.data.model.request.exam.RequestExamResultDto

data class ExamResultEntity(
    val examId:Int
){
    fun toRequestExamResultDto()=RequestExamResultDto(
        examId=examId
    )
}
