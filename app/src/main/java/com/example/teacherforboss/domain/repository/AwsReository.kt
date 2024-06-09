package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.domain.model.getPresingedUrlEntity
import com.example.teacherforboss.domain.model.presignedUrlListEntity

interface AwsReository {
    suspend fun getPresingedUrl(getPresingedUrlEntity: getPresingedUrlEntity):presignedUrlListEntity
}