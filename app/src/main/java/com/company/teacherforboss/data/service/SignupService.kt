package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.request.signup.RequestSignupDto
import com.company.teacherforboss.data.model.response.notification.NotificationSettingDto
import com.company.teacherforboss.data.model.response.signup.ResponseSignupDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SignupService {
    @POST("auth/signup")
    suspend fun signup(
        @Body signupRequest: RequestSignupDto
    ): BaseResponse<ResponseSignupDto>

    @POST("auth/signup/notifications/settings")
    suspend fun postNotificationSetting(
        @Header("Member-Id") memberId: Long,
        @Body notificationSettingDto: NotificationSettingDto
    ): BaseResponse<NotificationSettingDto>
}