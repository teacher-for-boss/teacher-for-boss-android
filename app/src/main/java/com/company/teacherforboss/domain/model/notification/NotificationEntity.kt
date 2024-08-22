package com.company.teacherforboss.domain.model.notification

import com.company.teacherforboss.presentation.ui.notification.NotificationType

data class NotificationEntity(
    val notificationId:Long,
    val notificationType: NotificationType,
    val contents:String,
    val read:Boolean,
    val createdAt:String
)
