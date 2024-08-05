package com.company.teacherforboss.data.repository

import com.company.teacherforboss.data.datasource.remote.AwsRemoteDataSource
import com.company.teacherforboss.domain.model.aws.getPresingedUrlEntity
import com.company.teacherforboss.domain.model.aws.presignedUrlListEntity
import com.company.teacherforboss.domain.repository.AwsReository
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