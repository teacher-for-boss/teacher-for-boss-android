package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.api.ApiClient
import com.example.teacherforboss.data.model.request.login.LoginRequest
import com.example.teacherforboss.data.model.request.login.SocialLoginRequest
import com.example.teacherforboss.data.model.request.signup.BusinessNumberCheckRequest
import com.example.teacherforboss.data.model.response.login.LoginResponse
import com.example.teacherforboss.data.model.response.login.socialLoginResponse
import com.example.teacherforboss.data.model.request.signup.EmailCheckRequest
import com.example.teacherforboss.data.model.response.signup.EmailCheckResponse
import com.example.teacherforboss.data.model.request.signup.EmailRequest
import com.example.teacherforboss.data.model.response.signup.EmailResponse
import com.example.teacherforboss.data.model.response.signup.SignupResponse
import com.example.teacherforboss.data.model.request.signup.PhoneCheckRequest
import com.example.teacherforboss.data.model.response.signup.PhoneCheckResponse
import com.example.teacherforboss.data.model.request.signup.PhoneRequest
import com.example.teacherforboss.data.model.request.signup.SignupBossRequest
import com.example.teacherforboss.data.model.request.signup.SignupTeacherRequest
import com.example.teacherforboss.data.model.response.signup.BusinessNumberCheckResponse
import com.example.teacherforboss.data.model.response.signup.PhoneResponse
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    companion object{
        fun getApi(): AuthService?{
            return ApiClient.client?.create(AuthService::class.java)
        }
    }
    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    )
    : Response<LoginResponse>


    @POST("auth/reissue")
    suspend fun loginReissue(
        @Header("RefreshToken") refreshToken: String)
    : Response<LoginResponse>

    @POST("auth/login/social")
    suspend fun socialLogin(
        @Query("socialType") socialType:Int,
        @Body socialLoginRequest: SocialLoginRequest
    )
    :Response<socialLoginResponse>
    @POST("auth/email")
    suspend fun emailUser(
        @Body emailRequest: EmailRequest
    )
    : Response<EmailResponse>

    @POST("auth/email/check")
    suspend fun emailCheck(
        @Body emailCheckRequest: EmailCheckRequest
    )
    : Response<EmailCheckResponse>

    @POST("auth/signup")
    suspend fun signupBoss(
        @Body signupRequest: SignupBossRequest
    )
    : Response<SignupResponse>
    @POST("auth/signup")
    suspend fun signupTeacher(
        @Body signupRequest: SignupTeacherRequest
    )
    : Response<SignupResponse>

    @POST("auth/phone")
    suspend fun phoneUser(
        @Body phoneRequest: PhoneRequest
    )
    :Response<PhoneResponse>

    @POST("auth/phone/check")
    suspend fun phoneCheck(
        @Body phoneCheckRequest: PhoneCheckRequest
    )
    :Response<PhoneCheckResponse>

    @POST("auth/teacher/business-number/check")
    suspend fun businessNumCheck(
        @Body businessNumberCheckRequest: BusinessNumberCheckRequest
    ):BaseResponse<BusinessNumberCheckResponse>
}