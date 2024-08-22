package com.company.teacherforboss.domain.usecase.notification

import com.company.teacherforboss.domain.model.notification.NotificationReadEntity
import com.company.teacherforboss.domain.repository.NotificationRepository


class ReadNotificationUseCase(
    private val notificationRepository: NotificationRepository
){
    suspend operator fun invoke(notificationId:Long): Result<NotificationReadEntity> = notificationRepository.readNotification(notificationId)
}