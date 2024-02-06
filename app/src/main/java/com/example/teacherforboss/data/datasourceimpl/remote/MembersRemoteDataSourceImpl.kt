package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.MembersRemoteDataSource
import com.example.teacherforboss.data.model.request.RequestSurveyDto
import com.example.teacherforboss.data.model.response.ResponseSurveyDto
import com.example.teacherforboss.data.service.MembersService
import com.example.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class MembersRemoteDataSourceImpl @Inject constructor(
    private val membersService: MembersService
) : MembersRemoteDataSource {
    override suspend fun postSurveyResult(requestSurveyDto: RequestSurveyDto): BaseResponse<ResponseSurveyDto> =
        membersService.postSurveyResult(request = requestSurveyDto)
}