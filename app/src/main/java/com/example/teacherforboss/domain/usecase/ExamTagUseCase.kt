package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.exams.ExamRequestTagEntity
import com.example.teacherforboss.domain.model.exams.ExamTagEntity
import com.example.teacherforboss.domain.repository.ExamRepository

data class ExamTagUseCase(
    private val examRepository: ExamRepository
){
    suspend operator fun invoke(examRequestTagEntity: ExamRequestTagEntity):ExamTagEntity
    = examRepository.getTag(examRequestTagEntity)
}
