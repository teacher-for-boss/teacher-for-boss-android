package com.company.teacherforboss.data.repositoryImpl

import com.company.teacherforboss.data.datasource.remote.NotificationRemoteDataSource
import com.company.teacherforboss.data.model.request.notification.NotificationRequestDto
import com.company.teacherforboss.domain.model.notification.NotificationListEntity
import com.company.teacherforboss.domain.model.notification.NotificationReadEntity
import com.company.teacherforboss.domain.model.notification.NotificationSettingEntity
import com.company.teacherforboss.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationRemoteDataSource: NotificationRemoteDataSource
):NotificationRepository {
    override suspend fun getNotifications(lastNotificationId: Long): Result<NotificationListEntity> =
        runCatching{
            notificationRemoteDataSource.getNotifications(notificationRequestDto = NotificationRequestDto(notificationId = lastNotificationId))
                .result.toNotificationListEntity()
        }

    override suspend fun readNotification(notificatioinId: Long): Result<NotificationReadEntity> =
        kotlin.runCatching {
            notificationRemoteDataSource.readNotification(notificationRequestDto = NotificationRequestDto(notificationId = notificatioinId))
                .result.toNotificationReadEntity()
        }

    override suspend fun getNotificationSetting(): Result<NotificationSettingEntity> =
        runCatching {
            notificationRemoteDataSource.getNotificationSetting().result.toNotificationSettingEntity()
        }

    override suspend fun postNotificationSetting(notificationSettingEntity: NotificationSettingEntity): Result<NotificationSettingEntity> =
        runCatching {
            notificationRemoteDataSource.postNotificationSetting(notificationSettingDto = notificationSettingEntity.toNotificationSettingDto()).result.toNotificationSettingEntity()
        }
}