package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.response.auth.LogoutResponse
import com.example.teacherforboss.util.base.BaseResponse

interface AuthRemoteDataSource {
    suspend fun logout():BaseResponse<LogoutResponse>
}