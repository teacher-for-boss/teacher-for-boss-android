package com.example.teacherforboss.login

import com.example.teacherforboss.signup.api.EmailCheckRequest
import com.example.teacherforboss.signup.api.EmailCheckResponse
import com.example.teacherforboss.signup.api.EmailRequest
import com.example.teacherforboss.signup.api.EmailResponse
import com.example.teacherforboss.signup.api.PhoneCheckRequest
import com.example.teacherforboss.signup.api.PhoneCheckResponse
import com.example.teacherforboss.signup.api.PhoneRequest
import com.example.teacherforboss.signup.api.PhoneResponse
import com.example.teacherforboss.signup.api.SignupRequest
import com.example.teacherforboss.signup.api.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {
    companion object{
        fun getApi():UserApi?{
            return ApiClient.client?.create(UserApi::class.java)
        }
    }
    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequest:LoginRequest)
    : Response<LoginResponse>


    @POST("auth/login/reissue")
    fun loginReissue(
        @Header("Authorization") refreshToken: String)
    : Response<LoginResponse>

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