package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.aws.getPresingedUrlEntity
import com.example.teacherforboss.domain.model.aws.presignedUrlListEntity

interface AwsReository {
    suspend fun getPresingedUrl(getPresingedUrlEntity: getPresingedUrlEntity): presignedUrlListEntity
}