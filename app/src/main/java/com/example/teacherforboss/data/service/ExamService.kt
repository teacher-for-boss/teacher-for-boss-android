package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.model.response.exam.ResponseExamResultWrongNotesDto
import com.example.teacherforboss.data.model.response.exam.ResponseExamResultDto
import com.example.teacherforboss.data.model.response.exam.ResponseCategory
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ExamService {
    @GET("${EXAMS}/{memberExamId}/result")
    suspend fun GetExamResult(
        @Path("memberExamId") examId:Int,
    )
    : BaseResponse<ResponseExamResultDto>

    @GET("${EXAMS}/member-exams/{memberExamId}/result/incorrect/list")
    suspend fun GetExamResultWrongNotes(
        @Path("memberExamId") examId:Int,
    ):BaseResponse<ResponseExamResultWrongNotesDto>
    @GET("${EXAMS}/category")
    suspend fun GetCategory(
    ):BaseResponse<ResponseCategory>

    companion object {
        const val EXAMS = "exams"
    }
}