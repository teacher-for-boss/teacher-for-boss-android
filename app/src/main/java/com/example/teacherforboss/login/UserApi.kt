package com.example.teacherforboss.login

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/login")
    suspend fun loginUser(
        @Body loginRequest:LoginRequest)
    : Response<LoginResponse>
        companion object{
            fun getApi():UserApi?{
                return ApiClient.client?.create(UserApi::class.java)
            }
        }

}