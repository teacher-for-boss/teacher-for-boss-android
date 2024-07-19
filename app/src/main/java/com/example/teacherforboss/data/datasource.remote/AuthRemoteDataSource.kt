package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.response.auth.AccountResponseDto
import com.example.teacherforboss.data.model.response.auth.LogoutResponse
import com.example.teacherforboss.data.model.response.auth.WithdrawResponse
import com.example.teacherforboss.util.base.BaseResponse

interface AuthRemoteDataSource {

    suspend fun getAccount():BaseResponse<AccountResponseDto>
    suspend fun logout():BaseResponse<LogoutResponse>

    suspend fun withdraw():BaseResponse<WithdrawResponse>
}