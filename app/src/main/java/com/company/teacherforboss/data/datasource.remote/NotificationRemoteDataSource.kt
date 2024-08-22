package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.request.notification.NotificationRequestDto
import com.company.teacherforboss.data.model.response.notification.NotificationListDto
import com.company.teacherforboss.data.model.response.notification.NotificationReadDto
import com.company.teacherforboss.util.base.BaseResponse

interface NotificationRemoteDataSource {
    suspend fun getNotifications():BaseResponse<NotificationListDto>

    suspend fun readNotification(notificationRequestDto: NotificationRequestDto):BaseResponse<NotificationReadDto>
}