package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.SignupRemoteDataSource
import com.example.teacherforboss.data.model.request.signup.RequestSignupDto
import com.example.teacherforboss.data.model.response.signup.ResponseSignupDto
import com.example.teacherforboss.data.service.SignupService
import com.example.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class SignupRemoteDataSourceImpl @Inject constructor(
    private val signupService: SignupService
) : SignupRemoteDataSource {
    override suspend fun signup(requestSignupDto: RequestSignupDto): BaseResponse<ResponseSignupDto> =
        signupService.signup(requestSignupDto)
}