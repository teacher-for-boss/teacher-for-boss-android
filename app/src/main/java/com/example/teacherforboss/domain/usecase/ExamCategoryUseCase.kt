package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.exams.ExamCategoryEntity
import com.example.teacherforboss.domain.repository.ExamRepository


class ExamCategoryUseCase (
    private val examRepository: ExamRepository
) {
    suspend operator fun invoke(): ExamCategoryEntity =
        examRepository.GetCategory()
}
