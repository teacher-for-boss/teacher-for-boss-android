package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.notification.NotificationListEntity
import com.company.teacherforboss.domain.model.notification.NotificationReadEntity

interface NotificationRepository {
    suspend fun getNotifications():Result<NotificationListEntity>

    suspend fun readNotification(notificatioinId:Long):Result<NotificationReadEntity>
}