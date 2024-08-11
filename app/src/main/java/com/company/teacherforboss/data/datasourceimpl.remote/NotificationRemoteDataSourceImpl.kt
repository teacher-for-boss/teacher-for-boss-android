package com.company.teacherforboss.data.datasourceimpl.remote

import com.company.teacherforboss.data.datasource.remote.NotificationRemoteDataSource
import com.company.teacherforboss.data.model.response.notification.NotificationListDto
import com.company.teacherforboss.data.service.NotificationService
import com.company.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class NotificationRemoteDataSourceImpl @Inject constructor(
    private val notificationService: NotificationService
):NotificationRemoteDataSource {
    override suspend fun getNotifications(): BaseResponse<NotificationListDto> = notificationService.getNotifications()

}