package com.example.teacherforboss.login

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

}