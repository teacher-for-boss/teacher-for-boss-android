package com.example.teacherforboss.domain.model.exams

import com.example.teacherforboss.data.model.response.exam.ResponseTagDto

data class ExamTagEntity(
    val tagList:List<ResponseTagDto.TagDto>
)
