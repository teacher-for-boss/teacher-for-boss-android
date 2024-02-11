package com.example.teacherforboss.domain.repository

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
import com.example.teacherforboss.data.model.request.signup.PhoneCheckRequest
import com.example.teacherforboss.data.model.response.signup.PhoneCheckResponse
import com.example.teacherforboss.data.model.request.signup.PhoneRequest
import com.example.teacherforboss.data.model.response.signup.PhoneResponse
import com.example.teacherforboss.domain.model.SignupEntity
import com.example.teacherforboss.domain.model.SurveyResultEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

// UserRepository.kt
interface UserRepository {
    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>?
    fun loginReissue(refreshToken: String): Response<LoginResponse>?
    suspend fun kakaoLogin(socialLoginRequest: SocialLoginRequest): Response<socialLoginResponse>?
    suspend fun naverLogin(socialLoginRequest: SocialLoginRequest): Response<socialLoginResponse>?
    suspend fun emailUser(emailRequest: EmailRequest): Response<EmailResponse>?
    suspend fun emailCheck(emailCheckRequest: EmailCheckRequest): Response<EmailCheckResponse>?
    suspend fun signupUser(signupRequest: SignupRequest): Response<SignupResponse>?

    suspend fun phoneUser(phoneRequest: PhoneRequest):Response<PhoneResponse>?

    suspend fun phoneCheck(phoneCheckRequest: PhoneCheckRequest):Response<PhoneCheckResponse>?
}
