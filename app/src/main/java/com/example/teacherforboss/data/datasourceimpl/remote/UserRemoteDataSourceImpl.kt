package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.UserRemoteDataSource
import com.example.teacherforboss.data.model.request.LoginRequest
import com.example.teacherforboss.data.model.response.LoginResponse
import com.example.teacherforboss.data.service.UserApi
import com.example.teacherforboss.data.model.request.SocialLoginRequest
import com.example.teacherforboss.data.model.response.socialLoginResponse
import com.example.teacherforboss.data.model.request.signup.EmailCheckRequest
import com.example.teacherforboss.data.model.response.signup.EmailCheckResponse
import com.example.teacherforboss.data.model.request.signup.EmailRequest
import com.example.teacherforboss.data.model.request.signup.PhoneCheckRequest
import com.example.teacherforboss.data.model.request.signup.PhoneRequest
import com.example.teacherforboss.data.model.response.signup.EmailResponse
import com.example.teacherforboss.data.model.request.signup.SignupRequest
import com.example.teacherforboss.data.model.response.signup.PhoneCheckResponse
import com.example.teacherforboss.data.model.response.signup.PhoneResponse
import com.example.teacherforboss.data.model.response.signup.SignupResponse
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

    override suspend fun phoneUser(phoneRequest: PhoneRequest): Response<PhoneResponse>? {
        return userApi.phoneUser(phoneRequest)
    }
    override suspend fun phoneCheck(phoneCheckRequest: PhoneCheckRequest): Response<PhoneCheckResponse>? {
        return userApi.phoneCheck(phoneCheckRequest)
    }


}