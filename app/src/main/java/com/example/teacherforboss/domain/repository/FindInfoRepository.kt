package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.data.model.request.findInfo.RequestFindEmailDto
import com.example.teacherforboss.data.model.request.findInfo.RequestFindPwDto
import com.example.teacherforboss.data.model.request.findInfo.RequestResetPwDto
import com.example.teacherforboss.data.model.request.signup.EmailRequest
import com.example.teacherforboss.data.model.response.findInfo.ResponseFindEmailDto
import com.example.teacherforboss.data.model.response.findInfo.ResponseFindPwDto
import com.example.teacherforboss.data.model.response.findInfo.ResponseResetPwDto
import com.example.teacherforboss.data.model.response.signup.EmailResponse
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.Response

interface FindInfoRepository {
    suspend fun findEmail(requestFindEmailDto: RequestFindEmailDto):BaseResponse<ResponseFindEmailDto>?
    suspend fun findPw(requestFindPwDto: RequestFindPwDto):BaseResponse<ResponseFindPwDto>?

    suspend fun resetPw(requestResetPwDto: RequestResetPwDto):BaseResponse<ResponseResetPwDto>?
}