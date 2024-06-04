package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.request.aws.RequestPresignedUrlDto
import com.example.teacherforboss.data.model.response.aws.ResponsePresignedUrlDto
import com.example.teacherforboss.util.base.BaseResponse

interface AwsRemoteDataSource {
    suspend fun getPresingedUrl(requestPresignedUrlDto: RequestPresignedUrlDto):BaseResponse<ResponsePresignedUrlDto>
}