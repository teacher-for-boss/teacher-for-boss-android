package com.company.teacherforboss.domain.usecase

import com.company.teacherforboss.domain.model.aws.getPresingedUrlEntity
import com.company.teacherforboss.domain.model.aws.presignedUrlListEntity
import com.company.teacherforboss.domain.repository.AwsReository

class PresignedUrlUseCase(
    private val awsReository: AwsReository
) {
    suspend operator fun invoke(getPresingedUrlEntity: getPresingedUrlEntity): presignedUrlListEntity =
        awsReository.getPresingedUrl(getPresingedUrlEntity)
}