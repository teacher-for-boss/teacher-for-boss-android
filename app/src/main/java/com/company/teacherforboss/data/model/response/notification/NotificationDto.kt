package com.company.teacherforboss.data.model.response.notification

import com.company.teacherforboss.domain.model.notification.NotificationEntity
import com.company.teacherforboss.presentation.ui.notification.NotificationType
import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("notificationId") val notificationId:Long,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content:String,
    @SerializedName("type") val type:String,
    @SerializedName("read") val read:Boolean,
    @SerializedName("createdAt")val createdAt:String,
    @SerializedName("data") val data: NotificationDataDto?
){
    data class NotificationDataDto (
        @SerializedName("type") val type: String,
        @SerializedName("questionId") val questionId: Long?,
        @SerializedName("postId") val postId: Long?
    ) {
        fun toNotificationDataEntity() = NotificationEntity.NotificationDataEntity(
            type = type,
            questionId = questionId,
            postId = postId
        )
    }

    fun mapType():NotificationType{
        return when(type.split("_")[0]) {
            NOTIFICATION_TEACHER-> NotificationType.TeacherTalk
            NOTIFICATION_BOSS-> NotificationType.BossTalk
            NOTIFICATION_HOME->NotificationType.Home
            NOTIFICATION_EVENT->NotificationType.Event
            NOTIFICATION_EXCHANGE->NotificationType.Exchange
            else-> NotificationType.Event
        }

    }

    fun toNotificationEntity()= NotificationEntity(
        notificationId=notificationId,
        title=title,
        content=content,
        type=mapType(),
        originalType=type,
        read=read,
        createdAt=createdAt,
        data = data?.toNotificationDataEntity()
    )

    companion object{
        const val NOTIFICATION_TEACHER="QUESTION"

        const val NOTIFICATION_BOSS="POST"

        const val NOTIFICATION_HOME="HOME"

        const val NOTIFICATION_EXCHANGE="EXCHANGE"

        const val NOTIFICATION_EVENT="EVENT"
    }
}
