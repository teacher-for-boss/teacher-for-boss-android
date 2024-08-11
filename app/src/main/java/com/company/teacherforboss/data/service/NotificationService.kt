package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.response.notification.NotificationListDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET

interface NotificationService {
    companion object{
        const val NOTIFICATION="notifications"
    }

    @GET
    suspend fun getNotifications():BaseResponse<NotificationListDto>
}