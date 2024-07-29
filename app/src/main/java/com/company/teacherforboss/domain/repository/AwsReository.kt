package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.aws.getPresingedUrlEntity
import com.company.teacherforboss.domain.model.aws.presignedUrlListEntity

interface AwsReository {
    suspend fun getPresingedUrl(getPresingedUrlEntity: getPresingedUrlEntity): presignedUrlListEntity
}