package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.AuthRemoteDataSource
import com.example.teacherforboss.data.model.response.auth.LogoutResponse
import com.example.teacherforboss.data.service.AuthService
import com.example.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) :AuthRemoteDataSource{
    override suspend fun logout(): BaseResponse<LogoutResponse> = authService.logout()

}