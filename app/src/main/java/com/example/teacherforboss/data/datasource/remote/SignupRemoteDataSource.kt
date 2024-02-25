package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.request.signup.RequestSignupDto
import com.example.teacherforboss.data.model.response.signup.ResponseSignupDto
import com.example.teacherforboss.util.base.BaseResponse

interface SignupRemoteDataSource {
    suspend fun signup(requestSignupDto: RequestSignupDto):BaseResponse<ResponseSignupDto>
}