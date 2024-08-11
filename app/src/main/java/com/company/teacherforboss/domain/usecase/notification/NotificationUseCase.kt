package com.company.teacherforboss.domain.usecase.notification

import com.company.teacherforboss.domain.model.notification.NotificationListEntity
import com.company.teacherforboss.domain.repository.NotificationRepository

class NotificationUseCase(
    private val notificationRepository: NotificationRepository
){
    suspend operator fun invoke():Result<NotificationListEntity> = notificationRepository.getNotifications()
}