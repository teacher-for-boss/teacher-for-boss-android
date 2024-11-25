package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.request.signup.MemberRequestDto
import com.company.teacherforboss.data.model.request.signup.RequestSignupDto
import com.company.teacherforboss.data.model.response.notification.NotificationSettingDto
import com.company.teacherforboss.data.model.response.signup.ResponseSignupDto
import com.company.teacherforboss.util.base.BaseResponse

interface SignupRemoteDataSource {
    suspend fun signup(requestSignupDto: RequestSignupDto):BaseResponse<ResponseSignupDto>

    suspend fun postNotificationSetting(memberRequestDto: MemberRequestDto, notificationSettingDto: NotificationSettingDto): BaseResponse<NotificationSettingDto>
}