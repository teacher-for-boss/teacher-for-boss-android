package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.ExamRemoteDataSource
import com.example.teacherforboss.data.model.request.exam.RequestExamResultDto
import com.example.teacherforboss.data.model.response.exam.ResponseExamResultDto
import com.example.teacherforboss.data.service.ExamService
import com.example.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class ExamRemoteDataSourceImpl @Inject constructor(
    private val examService: ExamService
):ExamRemoteDataSource {
    override suspend fun getExamResult(requestExamResultDto: RequestExamResultDto): BaseResponse<ResponseExamResultDto>
    =examService.GetExamResult(examId = requestExamResultDto.examId)

}