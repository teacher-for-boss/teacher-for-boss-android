package com.company.teacherforboss.domain.usecase.notification

import com.company.teacherforboss.domain.repository.NotificationRepository


class ReadNotificationUseCase(
    private val notificationRepository: NotificationRepository
){
    suspend operator fun invoke(notificationId:Long): Result<Unit> = notificationRepository.readNotification(notificationId)
}