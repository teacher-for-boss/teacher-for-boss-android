package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.exams.ExamResultEntity
import com.example.teacherforboss.domain.model.exams.ExamResultResultEntity
import com.example.teacherforboss.domain.model.exams.ExamResultWrongNotesEntity
import com.example.teacherforboss.domain.model.exams.ExamCategoryEntity
import com.example.teacherforboss.data.model.request.exam.RequestExamResultDto
import com.example.teacherforboss.data.model.response.exam.ResponseExamResultDto
import com.example.teacherforboss.util.base.BaseResponse

interface ExamRepository {
    suspend fun GetExamResult(examResultEntity: ExamResultEntity): ExamResultResultEntity

    suspend fun GetExamResultWrongNotes(examResultEntity: ExamResultEntity):ExamResultWrongNotesEntity
    suspend fun GetCategory() : ExamCategoryEntity
}