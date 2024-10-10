package com.company.teacherforboss.data.model.response.notification

import com.company.teacherforboss.domain.model.notification.NotificationSettingEntity
import com.google.gson.annotations.SerializedName

data class NotificationSettingDto (
    @SerializedName("serviceNotification") val serviceNotification: Boolean,
    @SerializedName("marketingNotification") val marketingNotification: MarketingNotificationSettingDto
) {
    data class MarketingNotificationSettingDto (
        @SerializedName("push") val push: Boolean,
        @SerializedName("email") val email: Boolean,
        @SerializedName("sms") val sms: Boolean
    ) {
        fun toMarketingNotificationSettingEntity() = NotificationSettingEntity.MarketingNotificationSettingEntity(
            push = push,
            email = email,
            sms = sms
            )
    }

    fun toNotificationSettingEntity() = NotificationSettingEntity(
        serviceNotification = serviceNotification,
        marketingNotification = marketingNotification.toMarketingNotificationSettingEntity()
    )
}