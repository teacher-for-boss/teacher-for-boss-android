package com.company.teacherforboss.presentation.ui.notification

data class dummyNotificationEntity(
    val notificationType: NotificationType,
    val notificationTitle:String,
    val notificationContent:String,
    val notificationTime:Int,
    val isClicked:Boolean
)
