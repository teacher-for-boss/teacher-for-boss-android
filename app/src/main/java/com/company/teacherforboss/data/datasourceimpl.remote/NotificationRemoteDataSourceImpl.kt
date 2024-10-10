package com.company.teacherforboss.data.datasourceimpl.remote

import com.company.teacherforboss.data.datasource.remote.NotificationRemoteDataSource
import com.company.teacherforboss.data.model.request.notification.NotificationRequestDto
import com.company.teacherforboss.data.model.response.notification.NotificationListDto
import com.company.teacherforboss.data.model.response.notification.NotificationReadDto
import com.company.teacherforboss.data.model.response.notification.NotificationSettingDto
import com.company.teacherforboss.data.service.NotificationService
import com.company.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class NotificationRemoteDataSourceImpl @Inject constructor(
    private val notificationService: NotificationService
):NotificationRemoteDataSource {
    override suspend fun getNotifications(notificationRequestDto: NotificationRequestDto): BaseResponse<NotificationListDto> =
        notificationService.getNotifications(notificationRequestDto.notificationId)

    override suspend fun readNotification(notificationRequestDto: NotificationRequestDto): BaseResponse<NotificationReadDto> = notificationService.readNotification(notificationId = notificationRequestDto.notificationId)

    override suspend fun getNotificationSetting(): BaseResponse<NotificationSettingDto> = notificationService.getNotificationSetting()

    override suspend fun postNotificationSetting(notificationSettingDto: NotificationSettingDto): BaseResponse<NotificationSettingDto> = notificationService.postNotificationSetting(notificationSettingDto = notificationSettingDto)
}