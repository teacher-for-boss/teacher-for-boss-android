package com.company.teacherforboss.data.datasourceimpl.remote

import com.company.teacherforboss.data.datasource.remote.SignupRemoteDataSource
import com.company.teacherforboss.data.model.request.signup.MemberRequestDto
import com.company.teacherforboss.data.model.request.signup.RequestSignupDto
import com.company.teacherforboss.data.model.response.notification.NotificationSettingDto
import com.company.teacherforboss.data.model.response.signup.ResponseSignupDto
import com.company.teacherforboss.data.service.SignupService
import com.company.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class SignupRemoteDataSourceImpl @Inject constructor(
    private val signupService: SignupService
) : SignupRemoteDataSource {
    override suspend fun signup(requestSignupDto: RequestSignupDto): BaseResponse<ResponseSignupDto> =
        signupService.signup(requestSignupDto)

    override suspend fun postNotificationSetting(memberRequestDto: MemberRequestDto, notificationSettingDto: NotificationSettingDto
    ): BaseResponse<NotificationSettingDto>
    = signupService.postNotificationSetting(memberId = memberRequestDto.memberId, notificationSettingDto = notificationSettingDto)
}