package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.request.exam.RequestExamResultDto
import com.example.teacherforboss.data.model.response.exam.ResponseExamResultWrongNotesDto
import com.example.teacherforboss.data.model.response.exam.ResponseCategory
import com.example.teacherforboss.data.model.response.exam.ResponseExamResultDto
import com.example.teacherforboss.util.base.BaseResponse

interface ExamRemoteDataSource {
    suspend fun getExamResult(requestExamResultDto: RequestExamResultDto):BaseResponse<ResponseExamResultDto>

    suspend fun getExamResultWrongNotes(requestExamResultDto: RequestExamResultDto):BaseResponse<ResponseExamResultWrongNotesDto>
    suspend fun getCategory():BaseResponse<ResponseCategory>
}