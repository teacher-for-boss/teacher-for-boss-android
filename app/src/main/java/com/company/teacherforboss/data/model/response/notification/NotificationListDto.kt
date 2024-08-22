package com.company.teacherforboss.data.model.response.notification

import com.company.teacherforboss.domain.model.notification.NotificationListEntity
import com.google.gson.annotations.SerializedName

data class NotificationListDto(
    @SerializedName("notificationList") val notificationList:List<NotificationDto>
){
    fun toNotificationListEntity()=NotificationListEntity(notificationList = notificationList.map { it.toNotificationEntity() })
}