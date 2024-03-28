package com.example.teacherforboss.domain.model.exams

import com.example.teacherforboss.data.model.request.exam.RequestTagDto

data class ExamRequestTagEntity(
    val examCategoryId:Long
){
    fun toRequestTagDto()= RequestTagDto(examCategoryId=examCategoryId)
}
