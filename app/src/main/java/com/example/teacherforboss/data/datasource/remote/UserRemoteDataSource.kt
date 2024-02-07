package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.request.LoginRequest
import com.example.teacherforboss.data.model.request.SocialLoginRequest
import com.example.teacherforboss.data.model.response.LoginResponse
import com.example.teacherforboss.data.model.response.socialLoginResponse
import com.example.teacherforboss.data.model.request.signup.EmailCheckRequest
import com.example.teacherforboss.data.model.response.signup.EmailCheckResponse
import com.example.teacherforboss.data.model.request.signup.EmailRequest
import com.example.teacherforboss.data.model.response.signup.EmailResponse
import com.example.teacherforboss.data.model.request.signup.SignupRequest
import com.example.teacherforboss.data.model.response.signup.SignupResponse
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>?
    fun loginReissue(refreshToken:String):Response<LoginResponse>?
    suspend fun kakaoLogin(socialLoginRequest:SocialLoginRequest):Response<socialLoginResponse>?
    suspend fun naverLogin(socialLoginRequest: SocialLoginRequest):Response<socialLoginResponse>?
    suspend fun emailUser(emailRequest: EmailRequest):Response<EmailResponse>?
    suspend fun emailCheck(emailCheckRequest: EmailCheckRequest):Response<EmailCheckResponse>?
    suspend fun signupUser(signupRequest: SignupRequest):Response<SignupResponse>?

}