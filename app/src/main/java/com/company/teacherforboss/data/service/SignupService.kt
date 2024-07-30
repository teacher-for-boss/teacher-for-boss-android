package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.request.signup.RequestSignupDto
import com.company.teacherforboss.data.model.response.signup.ResponseSignupDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupService {
    @POST("auth/signup")
    suspend fun signup(
        @Body signupRequest: RequestSignupDto
    ): BaseResponse<ResponseSignupDto>
}