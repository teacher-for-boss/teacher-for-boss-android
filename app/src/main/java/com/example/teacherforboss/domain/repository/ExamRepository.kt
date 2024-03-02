package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.data.model.request.exam.RequestExamResultDto
import com.example.teacherforboss.data.model.response.exam.ResponseExamResultDto
import com.example.teacherforboss.domain.model.ExamResultEntity
import com.example.teacherforboss.domain.model.ExamResultResultEntity
import com.example.teacherforboss.util.base.BaseResponse
import kotlinx.coroutines.flow.Flow

interface ExamRepository {
    suspend fun GetExamResult(examResultEntity: ExamResultEntity): ExamResultResultEntity
}