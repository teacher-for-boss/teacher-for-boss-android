package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.api.ApiClient
import com.example.teacherforboss.data.model.request.LoginRequest
import com.example.teacherforboss.data.model.request.findInfo.RequestFindEmailDto
import com.example.teacherforboss.data.model.request.findInfo.RequestFindPwDto
import com.example.teacherforboss.data.model.response.LoginResponse
import com.example.teacherforboss.data.model.response.findInfo.ResponseFindEmailDto
import com.example.teacherforboss.data.model.response.findInfo.ResponseFindPwDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FindInfoService {
    companion object{
        fun getApi(): FindInfoService?{
            return ApiClient.client?.create(FindInfoService::class.java)
        }
    }
    @POST("auth/find/email")
    suspend fun findEmail(
        @Body requestFindEmailDto: RequestFindEmailDto): Response<ResponseFindEmailDto>

    @POST("auth/find/password")
    suspend fun findPw(
        @Body requestFindPwDto: RequestFindPwDto):BaseResponse<ResponseFindPwDto>

}