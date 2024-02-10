package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.api.ApiClient
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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    companion object{
        fun getApi(): UserApi?{
            return ApiClient.client?.create(UserApi::class.java)
        }
    }
    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    )
    : Response<LoginResponse>


    @POST("auth/login/reissue")
    fun loginReissue(
        @Header("Authorization") refreshToken: String)
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
    suspend fun signupUser(
        @Body signupRequest: SignupRequest
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
}