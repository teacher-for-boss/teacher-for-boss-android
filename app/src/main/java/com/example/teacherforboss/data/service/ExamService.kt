package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.api.AuthApiClient
import com.example.teacherforboss.data.model.request.exam.RequestExamResultDto
import com.example.teacherforboss.data.model.request.login.LoginRequest
import com.example.teacherforboss.data.model.response.exam.ResponseExamResultDto
import com.example.teacherforboss.data.model.response.login.LoginResponse
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ExamService {
    companion object{
        fun getApi(): ExamService?{
            return AuthApiClient.client?.create(ExamService::class.java)
        }

    }

    @GET("exams/{examId}/result")
    suspend fun GetExamResult(
        @Path("examId") examId:Int,
//        @Path("examId") examId: RequestExamResultDto,
    )
    : BaseResponse<ResponseExamResultDto>
}