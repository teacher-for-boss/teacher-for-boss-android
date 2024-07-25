package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.api.ApiClient
import com.company.teacherforboss.data.model.request.findInfo.RequestFindEmailDto
import com.company.teacherforboss.data.model.request.findInfo.RequestFindPwDto
import com.company.teacherforboss.data.model.request.findInfo.RequestResetPwDto
import com.company.teacherforboss.data.model.response.findInfo.ResponseFindEmailDto
import com.company.teacherforboss.data.model.response.findInfo.ResponseFindPwDto
import com.company.teacherforboss.data.model.response.findInfo.ResponseResetPwDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface FindInfoService {
    companion object{
        fun getApi(): FindInfoService?{
            return ApiClient.client?.create(FindInfoService::class.java)
        }
    }
    @POST("auth/find/email")
    suspend fun findEmail(
        @Body requestFindEmailDto: RequestFindEmailDto): BaseResponse<ResponseFindEmailDto>

    @POST("auth/find/password")
    suspend fun findPw(
        @Body requestFindPwDto: RequestFindPwDto):BaseResponse<ResponseFindPwDto>

    @PATCH("auth/resetPassword")
    suspend fun resetPw(
        @Body requestResetPw: RequestResetPwDto
    ):BaseResponse<ResponseResetPwDto>

}