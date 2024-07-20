package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.response.auth.AccountResponseDto
import com.example.teacherforboss.util.base.BaseResponse

interface MemberRemoteDataSource {
    suspend fun getAccount(): BaseResponse<AccountResponseDto>

}