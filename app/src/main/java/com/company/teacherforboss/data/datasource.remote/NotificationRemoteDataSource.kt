package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.request.notification.NotificationRequestDto
import com.company.teacherforboss.data.model.response.notification.NotificationListDto
import com.company.teacherforboss.data.model.response.notification.NotificationReadDto
import com.company.teacherforboss.data.model.response.notification.NotificationSettingDto
import com.company.teacherforboss.util.base.BaseResponse

interface NotificationRemoteDataSource {
    suspend fun getNotifications(notificationRequestDto: NotificationRequestDto):BaseResponse<NotificationListDto>

    suspend fun readNotification(notificationRequestDto: NotificationRequestDto):BaseResponse<NotificationReadDto>

    suspend fun getNotificationSetting(): BaseResponse<NotificationSettingDto>

    suspend fun postNotificationSetting(notificationSettingDto: NotificationSettingDto): BaseResponse<NotificationSettingDto>
}