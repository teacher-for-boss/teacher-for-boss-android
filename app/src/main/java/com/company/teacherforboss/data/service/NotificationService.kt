package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.response.notification.NotificationListDto
import com.company.teacherforboss.data.model.response.notification.NotificationReadDto
import com.company.teacherforboss.data.model.response.notification.NotificationSettingDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface NotificationService {
    companion object{
        const val NOTIFICATION="notifications"
    }

    @GET("${NOTIFICATION}")
    suspend fun getNotifications():BaseResponse<NotificationListDto>

    @PATCH("${NOTIFICATION}")
    suspend fun readNotification(
        @Path("notificationId") notificationId:Long,
    ):BaseResponse<NotificationReadDto>

    @GET("${NOTIFICATION}/settings")
    suspend fun getNotificationSetting(): BaseResponse<NotificationSettingDto>
}