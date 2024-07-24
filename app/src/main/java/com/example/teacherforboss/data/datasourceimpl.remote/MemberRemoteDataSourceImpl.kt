package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.MemberRemoteDataSource
import com.example.teacherforboss.data.model.response.auth.AccountResponseDto
import com.example.teacherforboss.data.model.response.mypage.ProfileResponseDto
import com.example.teacherforboss.data.service.AuthService
import com.example.teacherforboss.data.service.MemberService
import com.example.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class MemberRemoteDataSourceImpl @Inject constructor(
    private val memberService: MemberService
) :MemberRemoteDataSource{
    override suspend fun getAccount(): BaseResponse<AccountResponseDto> =  memberService.getAccounts()

    override suspend fun getProfile(): BaseResponse<ProfileResponseDto> = memberService.getProfile()

}