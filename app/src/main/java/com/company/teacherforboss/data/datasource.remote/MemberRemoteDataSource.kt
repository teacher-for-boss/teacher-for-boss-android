package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.response.auth.AccountResponseDto
import com.company.teacherforboss.data.model.response.mypage.ProfileResponseDto
import com.company.teacherforboss.util.base.BaseResponse

interface MemberRemoteDataSource {
    suspend fun getAccount(): BaseResponse<AccountResponseDto>

    suspend fun getProfile(): BaseResponse<ProfileResponseDto>

}