package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.model.response.exam.ResponseExamResultDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ExamService {
    @GET("${EXAMS}/{examId}/result")
    suspend fun GetExamResult(
        @Path("examId") examId:Int,
    )
    : BaseResponse<ResponseExamResultDto>

    companion object {
        const val EXAMS = "exams"
    }
}