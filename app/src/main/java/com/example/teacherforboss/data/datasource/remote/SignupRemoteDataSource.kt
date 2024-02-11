package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.request.RequestSignupDto
import com.example.teacherforboss.data.model.request.signup.SignupRequest
import com.example.teacherforboss.data.model.response.ResponseSignupDto
import com.example.teacherforboss.data.model.response.signup.SignupResponse
import com.example.teacherforboss.util.base.BaseResponse

interface SignupRemoteDataSource {
    suspend fun signup(requestSignupDto: RequestSignupDto):BaseResponse<ResponseSignupDto>
}