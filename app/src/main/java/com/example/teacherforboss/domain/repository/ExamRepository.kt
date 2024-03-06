package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.exams.ExamResultEntity
import com.example.teacherforboss.domain.model.exams.ExamResultResultEntity
import com.example.teacherforboss.domain.model.exams.ExamResultWrongNotesEntity

interface ExamRepository {
    suspend fun GetExamResult(examResultEntity: ExamResultEntity): ExamResultResultEntity

    suspend fun GetExamResultWrongNotes(examResultEntity: ExamResultEntity):ExamResultWrongNotesEntity
}