package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.ExamResultEntity
import com.example.teacherforboss.domain.model.ExamResultResultEntity
import com.example.teacherforboss.domain.repository.ExamRepository
import kotlinx.coroutines.flow.Flow

class ExamResultUseCase(
    private val examRepository: ExamRepository
) {
    suspend operator fun invoke(examResultEntity: ExamResultEntity):ExamResultResultEntity=
        examRepository.GetExamResult(examResultEntity)
}