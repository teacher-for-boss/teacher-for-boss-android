package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.request.LoginRequest
import com.example.teacherforboss.data.model.request.SocialLoginRequest
import com.example.teacherforboss.data.model.response.LoginResponse
import com.example.teacherforboss.data.model.response.socialLoginResponse
import com.example.teacherforboss.presentation.ui.auth.signup.api.EmailCheckRequest
import com.example.teacherforboss.presentation.ui.auth.signup.api.EmailCheckResponse
import com.example.teacherforboss.presentation.ui.auth.signup.api.EmailRequest
import com.example.teacherforboss.presentation.ui.auth.signup.api.EmailResponse
import com.example.teacherforboss.presentation.ui.auth.signup.api.SignupRequest
import com.example.teacherforboss.presentation.ui.auth.signup.api.SignupResponse
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