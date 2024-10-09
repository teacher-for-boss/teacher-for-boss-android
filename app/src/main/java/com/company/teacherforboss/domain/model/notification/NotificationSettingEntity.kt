package com.company.teacherforboss.domain.model.notification

data class NotificationSettingEntity(
    val serviceNotification: Boolean,
    val marketingNotification: MarketingNotificationSettingEntity
) {
    data class MarketingNotificationSettingEntity(
        val push: Boolean,
        val email: Boolean,
        val sms: Boolean
    )
}