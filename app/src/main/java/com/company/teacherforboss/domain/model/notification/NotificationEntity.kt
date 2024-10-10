package com.company.teacherforboss.domain.model.notification

import com.company.teacherforboss.presentation.ui.notification.NotificationType

data class NotificationEntity(
    val notificationId:Long,
    val title: String,
    val content: String,
    val type: NotificationType,
    val read:Boolean,
    val createdAt:String
)
