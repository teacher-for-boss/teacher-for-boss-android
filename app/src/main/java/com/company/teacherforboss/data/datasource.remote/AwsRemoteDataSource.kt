package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.request.aws.RequestPresignedUrlDto
import com.company.teacherforboss.data.model.response.aws.ResponsePresignedUrlDto
import com.company.teacherforboss.util.base.BaseResponse

interface AwsRemoteDataSource {
    suspend fun getPresingedUrl(requestPresignedUrlDto: RequestPresignedUrlDto):BaseResponse<ResponsePresignedUrlDto>
}