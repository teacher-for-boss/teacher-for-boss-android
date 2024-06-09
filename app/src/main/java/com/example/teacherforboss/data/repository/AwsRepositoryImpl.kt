package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.datasource.remote.AwsRemoteDataSource
import com.example.teacherforboss.domain.model.getPresingedUrlEntity
import com.example.teacherforboss.domain.model.presignedUrlListEntity
import com.example.teacherforboss.domain.repository.AwsReository
import javax.inject.Inject

class AwsRepositoryImpl @Inject constructor(
    private val awsRemoteDataSource: AwsRemoteDataSource
): AwsReository{
    override suspend fun getPresingedUrl(getPresingedUrlEntity: getPresingedUrlEntity): presignedUrlListEntity {
        return runCatching {
            awsRemoteDataSource.getPresingedUrl(
                requestPresignedUrlDto = getPresingedUrlEntity.toGetPresignedUrlDto()
            ).result.toPresignedUrlListEntity()
        }.getOrElse { err->
            throw err
        }
    }
}