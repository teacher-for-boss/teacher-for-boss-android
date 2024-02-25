package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.request.survey.RequestSurveyDto
import com.example.teacherforboss.data.model.response.survey.ResponseSurveyDto
import com.example.teacherforboss.util.base.BaseResponse

interface MembersRemoteDataSource {
    suspend fun postSurveyResult(requestSurveyDto: RequestSurveyDto): BaseResponse<ResponseSurveyDto>
}