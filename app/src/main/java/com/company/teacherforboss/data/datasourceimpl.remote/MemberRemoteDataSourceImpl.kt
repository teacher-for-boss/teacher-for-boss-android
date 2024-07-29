package com.company.teacherforboss.data.datasourceimpl.remote

import com.company.teacherforboss.data.datasource.remote.MemberRemoteDataSource
import com.company.teacherforboss.data.model.response.auth.AccountResponseDto
import com.company.teacherforboss.data.model.response.mypage.ProfileResponseDto
import com.company.teacherforboss.data.service.MemberService
import com.company.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class MemberRemoteDataSourceImpl @Inject constructor(
    private val memberService: MemberService
) :MemberRemoteDataSource{
    override suspend fun getAccount(): BaseResponse<AccountResponseDto> =  memberService.getAccounts()

    override suspend fun getProfile(): BaseResponse<ProfileResponseDto> = memberService.getProfile()

}