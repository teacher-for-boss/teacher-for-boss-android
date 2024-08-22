package com.company.teacherforboss.data.model.response.notification

import com.company.teacherforboss.domain.model.notification.NotificationEntity
import com.company.teacherforboss.presentation.ui.notification.NotificationType
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.io.Serial

data class NotificationDto(
    @SerializedName("notificationId") val notificationId:Long,
    @SerializedName("type") val type:String,
    @SerializedName("contents") val contents:String,
    @SerializedName("read") val read:Boolean,
    @SerializedName("createdAt")val createdAt:String
){
    fun mapType():NotificationType{
        return when(type){
            NOTIFICATION_TEACHER-> NotificationType.TeacherTalk
            NOTIFICATION_BOSS-> NotificationType.TeacherTalk
            NOTIFICATION_HOME->NotificationType.Home
            NOTIFICATION_EVENT->NotificationType.Event
            NOTIFICATION_EXCHANGE->NotificationType.Exchange
            else-> NotificationType.Event
        }

    }

    fun toNotificationEntity()= NotificationEntity(
        notificationId=notificationId,
        notificationType = mapType(),
        contents=contents,
        read=read,
        createdAt=createdAt
    )

    companion object{
        const val NOTIFICATION_TEACHER=""

        const val NOTIFICATION_BOSS=""

        const val NOTIFICATION_HOME=""

        const val NOTIFICATION_EXCHANGE=""

        const val NOTIFICATION_EVENT=""
    }
}
