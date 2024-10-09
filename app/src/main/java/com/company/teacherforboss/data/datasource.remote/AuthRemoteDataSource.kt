package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.response.auth.LogoutResponse
import com.company.teacherforboss.data.model.response.auth.WithdrawResponse
import com.company.teacherforboss.util.base.BaseResponse

interface AuthRemoteDataSource {

    suspend fun logout(fcmToken:String):BaseResponse<LogoutResponse>

    suspend fun withdraw():BaseResponse<WithdrawResponse>
}