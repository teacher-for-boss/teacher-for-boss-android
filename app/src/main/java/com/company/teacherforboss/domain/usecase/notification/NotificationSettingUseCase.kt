package com.company.teacherforboss.domain.usecase.notification

import com.company.teacherforboss.domain.model.notification.NotificationSettingEntity
import com.company.teacherforboss.domain.repository.NotificationRepository

class NotificationSettingUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(): Result<NotificationSettingEntity> = notificationRepository.getNotificationSetting()
}