package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.exams.ExamResultEntity
import com.example.teacherforboss.domain.model.exams.ExamResultResultEntity
import com.example.teacherforboss.domain.repository.ExamRepository

class ExamResultUseCase(
    private val examRepository: ExamRepository
) {
    suspend operator fun invoke(examResultEntity: ExamResultEntity): ExamResultResultEntity =
        examRepository.GetExamResult(examResultEntity)
}