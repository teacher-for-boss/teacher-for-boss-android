package com.company.teacherforboss.data.model.response.notification

import com.company.teacherforboss.domain.model.notification.NotificationListEntity
import com.google.gson.annotations.SerializedName

data class NotificationListDto(
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("notificationList") val notificationList:List<NotificationDto>
){
    fun toNotificationListEntity()=NotificationListEntity(
        hasNext = hasNext,
        notificationList = notificationList.map { it.toNotificationEntity() })
}