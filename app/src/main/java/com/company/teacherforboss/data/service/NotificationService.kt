package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.response.notification.NotificationListDto
import com.company.teacherforboss.data.model.response.notification.NotificationReadDto
import com.company.teacherforboss.data.model.response.notification.NotificationSettingDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NotificationService {
    companion object{
        const val NOTIFICATION="notifications"
    }

    @GET("${NOTIFICATION}?")
    suspend fun getNotifications(
        @Query("lastNotificationId") lastNotificationId: Long
    ):BaseResponse<NotificationListDto>

    @PATCH("${NOTIFICATION}")
    suspend fun readNotification(
        @Path("notificationId") notificationId:Long,
    ):BaseResponse<NotificationReadDto>

    @GET("${NOTIFICATION}/settings")
    suspend fun getNotificationSetting(): BaseResponse<NotificationSettingDto>

    @POST("${NOTIFICATION}/settings")
    suspend fun postNotificationSetting(
        @Body notificationSettingDto: NotificationSettingDto
    ): BaseResponse<NotificationSettingDto>
}