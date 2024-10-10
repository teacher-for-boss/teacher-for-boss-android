package com.company.teacherforboss.domain.usecase.notification

import com.company.teacherforboss.domain.model.notification.NotificationSettingEntity
import com.company.teacherforboss.domain.repository.NotificationRepository

class NotificationSettingPostUseCase(private val notificationRepository: NotificationRepository) {
    suspend operator fun invoke(notificationSettingEntity: NotificationSettingEntity): Result<NotificationSettingEntity> =
        notificationRepository.postNotificationSetting(notificationSettingEntity = notificationSettingEntity)
}