package com.company.teacherforboss.data.datasourceimpl.remote

import com.company.teacherforboss.data.datasource.remote.AwsRemoteDataSource
import com.company.teacherforboss.data.model.request.aws.RequestPresignedUrlDto
import com.company.teacherforboss.data.model.response.aws.ResponsePresignedUrlDto
import com.company.teacherforboss.data.service.awsService
import com.company.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class AwsRemoteDataSourceImpl @Inject constructor(
    private val awsService: awsService
):AwsRemoteDataSource{
    override suspend fun getPresingedUrl(requestPresignedUrlDto: RequestPresignedUrlDto): BaseResponse<ResponsePresignedUrlDto>
    = awsService.getPresingedUrl(uuid=requestPresignedUrlDto.uuid,lastIndex=requestPresignedUrlDto.lastIndex, imageCount = requestPresignedUrlDto.imageCount,origin=requestPresignedUrlDto.origin)


}