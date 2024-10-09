package com.company.teacherforboss.domain.model.notification

import com.company.teacherforboss.data.model.response.notification.NotificationSettingDto

data class NotificationSettingEntity(
    val serviceNotification: Boolean,
    val marketingNotification: MarketingNotificationSettingEntity
) {
    data class MarketingNotificationSettingEntity(
        val push: Boolean,
        val email: Boolean,
        val sms: Boolean
    ) {
        fun toMarketingNotificationSettingDto() = NotificationSettingDto.MarketingNotificationSettingDto(
            push = push,
            email = email,
            sms = sms
        )
    }

    fun toNotificationSettingDto() = NotificationSettingDto(
        serviceNotification = serviceNotification,
        marketingNotification = marketingNotification.toMarketingNotificationSettingDto()
    )
}