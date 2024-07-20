package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.api.ApiClient
import com.example.teacherforboss.data.model.response.auth.AccountResponseDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET

interface MemberService {
    companion object{
        const val MEMBER="members"
    }
    @GET("${MEMBER}/accounts")
    suspend fun getAccounts()
            : BaseResponse<AccountResponseDto>
}