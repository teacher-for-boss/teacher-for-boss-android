package com.company.teacherforboss.domain.repository

import com.company.teacherforboss.data.model.request.findInfo.RequestFindEmailDto
import com.company.teacherforboss.data.model.request.findInfo.RequestFindPwDto
import com.company.teacherforboss.data.model.request.findInfo.RequestResetPwDto
import com.company.teacherforboss.data.model.response.findInfo.ResponseFindEmailDto
import com.company.teacherforboss.data.model.response.findInfo.ResponseFindPwDto
import com.company.teacherforboss.data.model.response.findInfo.ResponseResetPwDto
import com.company.teacherforboss.util.base.BaseResponse

interface FindInfoRepository {
    suspend fun findEmail(requestFindEmailDto: RequestFindEmailDto):BaseResponse<ResponseFindEmailDto>?
    suspend fun findPw(requestFindPwDto: RequestFindPwDto):BaseResponse<ResponseFindPwDto>?

    suspend fun resetPw(requestResetPwDto: RequestResetPwDto):BaseResponse<ResponseResetPwDto>?
}