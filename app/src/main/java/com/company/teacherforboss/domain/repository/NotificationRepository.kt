package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.domain.model.notification.NotificationListEntity
import com.company.teacherforboss.domain.model.notification.NotificationReadEntity
import com.company.teacherforboss.domain.model.notification.NotificationSettingEntity

interface NotificationRepository {
    suspend fun getNotifications(lastNotificationId: Long):Result<NotificationListEntity>

    suspend fun readNotification(notificatioinId:Long):Result<NotificationReadEntity>

    suspend fun getNotificationSetting(): Result<NotificationSettingEntity>

    suspend fun postNotificationSetting(notificationSettingEntity: NotificationSettingEntity): Result<NotificationSettingEntity>
}