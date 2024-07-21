package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.AwsRemoteDataSource
import com.example.teacherforboss.data.model.request.aws.RequestPresignedUrlDto
import com.example.teacherforboss.data.model.response.aws.ResponsePresignedUrlDto
import com.example.teacherforboss.data.service.awsService
import com.example.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class AwsRemoteDataSourceImpl @Inject constructor(
    private val awsService: awsService
):AwsRemoteDataSource{
    override suspend fun getPresingedUrl(requestPresignedUrlDto: RequestPresignedUrlDto): BaseResponse<ResponsePresignedUrlDto>
    = awsService.getPresingedUrl(uuid=requestPresignedUrlDto.uuid,lastIndex=requestPresignedUrlDto.lastIndex, imageCount = requestPresignedUrlDto.imageCount,origin=requestPresignedUrlDto.origin, fileType = requestPresignedUrlDto.fileType)


}