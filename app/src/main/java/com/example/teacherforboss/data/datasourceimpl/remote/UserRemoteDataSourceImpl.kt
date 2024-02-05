package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.UserRemoteDataSource
import com.example.teacherforboss.data.model.request.LoginRequest
import com.example.teacherforboss.data.model.response.LoginResponse
import com.example.teacherforboss.data.api.UserApi
import com.example.teacherforboss.data.model.request.SocialLoginRequest
import com.example.teacherforboss.data.model.response.socialLoginResponse
import com.example.teacherforboss.presentation.ui.auth.signup.api.EmailCheckRequest
import com.example.teacherforboss.presentation.ui.auth.signup.api.EmailCheckResponse
import com.example.teacherforboss.presentation.ui.auth.signup.api.EmailRequest
import com.example.teacherforboss.presentation.ui.auth.signup.api.EmailResponse
import com.example.teacherforboss.presentation.ui.auth.signup.api.SignupRequest
import com.example.teacherforboss.presentation.ui.auth.signup.api.SignupResponse
import retrofit2.Response

class UserRemoteDataSourceImpl(private val userApi: UserApi):UserRemoteDataSource {
    override suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return userApi.loginUser(loginRequest)
    }

    override fun loginReissue(refreshToken: String): Response<LoginResponse>? {
        return userApi.loginReissue("Bearer ${refreshToken}")
    }

    override suspend fun kakaoLogin(socialLoginRequest: SocialLoginRequest): Response<socialLoginResponse>? {
        return userApi.socialLogin(socialType = 2,socialLoginRequest)
    }

    override suspend fun naverLogin(socialLoginRequest: SocialLoginRequest): Response<socialLoginResponse>? {
        return userApi.socialLogin(socialType = 3,socialLoginRequest)
    }

    override suspend fun emailUser(emailRequest: EmailRequest): Response<EmailResponse>? {
        return userApi.emailUser(emailRequest)
    }

    override suspend fun emailCheck(emailCheckRequest: EmailCheckRequest): Response<EmailCheckResponse>? {
        return userApi.emailCheck(emailCheckRequest)
    }

    override suspend fun signupUser(signupRequest: SignupRequest): Response<SignupResponse>? {
        return userApi.signupUser(signupRequest)
    }
}