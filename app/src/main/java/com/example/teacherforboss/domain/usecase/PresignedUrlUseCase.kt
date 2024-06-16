package com.example.teacherforboss.domain.usecase

import com.example.teacherforboss.domain.model.aws.getPresingedUrlEntity
import com.example.teacherforboss.domain.model.aws.presignedUrlListEntity
import com.example.teacherforboss.domain.repository.AwsReository

class PresignedUrlUseCase(
    private val awsReository: AwsReository
) {
    suspend operator fun invoke(getPresingedUrlEntity: getPresingedUrlEntity): presignedUrlListEntity =
        awsReository.getPresingedUrl(getPresingedUrlEntity)
}