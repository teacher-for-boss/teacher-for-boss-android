package com.company.teacherforboss.domain.usecase.notification

import com.company.teacherforboss.domain.model.notification.NotificationListEntity
import com.company.teacherforboss.domain.repository.NotificationRepository

class NotificationUseCase(
    private val notificationRepository: NotificationRepository
){
    suspend operator fun invoke(lastNotificationId: Long):Result<NotificationListEntity> =
        notificationRepository.getNotifications(lastNotificationId = lastNotificationId)
}