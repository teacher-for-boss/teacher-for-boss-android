package com.company.teacherforboss.domain.model.notification

data class NotificationListEntity(
    val hasNext: Boolean,
    val notificationList:List<NotificationEntity>
)