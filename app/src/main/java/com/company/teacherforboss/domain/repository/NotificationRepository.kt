package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.notification.NotificationListEntity

interface NotificationRepository {
    suspend fun getNotifications():Result<NotificationListEntity>
}