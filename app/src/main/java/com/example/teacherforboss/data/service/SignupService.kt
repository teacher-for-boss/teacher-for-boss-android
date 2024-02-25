package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.model.request.signup.RequestSignupDto
import com.example.teacherforboss.data.model.response.signup.ResponseSignupDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupService {
    @POST("auth/signup")
    suspend fun signup(
        @Body signupRequest: RequestSignupDto
    ): BaseResponse<ResponseSignupDto>
}