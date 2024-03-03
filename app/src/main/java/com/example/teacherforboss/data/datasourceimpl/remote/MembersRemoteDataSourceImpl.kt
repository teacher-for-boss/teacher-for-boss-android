package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.MembersRemoteDataSource
import com.example.teacherforboss.data.model.request.survey.RequestSurveyDto
import com.example.teacherforboss.data.model.response.members.ResponseGetProfileDto
import com.example.teacherforboss.data.model.response.survey.ResponseSurveyDto
import com.example.teacherforboss.data.service.MembersService
import com.example.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class MembersRemoteDataSourceImpl @Inject constructor(
    private val membersService: MembersService
) : MembersRemoteDataSource {
    override suspend fun postSurveyResult(requestSurveyDto: RequestSurveyDto): BaseResponse<ResponseSurveyDto> =
        membersService.postSurveyResult(request = requestSurveyDto)
    override suspend fun getProfile(): BaseResponse<ResponseGetProfileDto> =
        membersService.getProfile()
}
