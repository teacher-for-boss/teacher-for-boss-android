package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.exams.ExamResultEntity
import com.example.teacherforboss.domain.model.exams.ExamResultWrongNotesEntity
import com.example.teacherforboss.domain.repository.ExamRepository

class ExamResultWrongNotesUseCase(
    private val examRepository: ExamRepository
) {
    suspend operator fun invoke(examResultEntity: ExamResultEntity):ExamResultWrongNotesEntity=
        examRepository.GetExamResultWrongNotes(examResultEntity)
}