package com.company.teacherforboss.data.model.response.notification

import com.company.teacherforboss.domain.model.notification.NotificationReadEntity
import com.google.gson.annotations.SerializedName

data class NotificationReadDto(
    @SerializedName("read") val read:Boolean,
    @SerializedName("updatedAt") val updatedAt:String
){
    fun toNotificationReadEntity()=NotificationReadEntity(
        read=read,
        updatedAt=updatedAt
    )
}
