package com.company.teacherforboss.data.datasourceimpl.remote

import com.company.teacherforboss.data.datasource.remote.AuthRemoteDataSource
import com.company.teacherforboss.data.model.response.auth.LogoutResponse
import com.company.teacherforboss.data.model.response.auth.WithdrawResponse
import com.company.teacherforboss.data.service.AuthService
import com.company.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) :AuthRemoteDataSource{
    override suspend fun logout(fcmToken:String): BaseResponse<LogoutResponse> = authService.logout(fcmToken = fcmToken)
    override suspend fun withdraw(): BaseResponse<WithdrawResponse> = authService.withdraw()

}