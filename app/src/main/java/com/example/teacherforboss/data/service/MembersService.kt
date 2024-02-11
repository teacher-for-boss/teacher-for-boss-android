package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.model.request.RequestSurveyDto
import com.example.teacherforboss.data.model.response.ResponseSurveyDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MembersService {
    @POST("$MEMBERS/$SURVEY")
    suspend fun postSurveyResult(
        @Body request: RequestSurveyDto
    ): BaseResponse<ResponseSurveyDto>

    companion object {
        const val MEMBERS = "members"
        const val SURVEY = "survey"
    }
}