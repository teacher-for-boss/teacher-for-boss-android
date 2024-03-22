package com.example.teacherforboss.domain.model.exams

import com.example.teacherforboss.data.model.response.exam.ResponseCategory

data class ExamCategoryEntity (
    val examCategoryList: List<ResponseCategory.ExamCategory>
)