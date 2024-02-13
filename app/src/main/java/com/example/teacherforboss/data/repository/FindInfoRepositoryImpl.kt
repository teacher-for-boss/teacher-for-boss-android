package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.model.request.findInfo.RequestFindEmailDto
import com.example.teacherforboss.data.model.request.findInfo.RequestFindPwDto
import com.example.teacherforboss.data.model.response.findInfo.ResponseFindEmailDto
import com.example.teacherforboss.data.model.response.findInfo.ResponseFindPwDto
import com.example.teacherforboss.data.service.FindInfoService
import com.example.teacherforboss.domain.repository.FindInfoRepository
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.Response

class FindInfoRepositoryImpl:FindInfoRepository {
    override suspend fun findEmail(requestFindEmailDto: RequestFindEmailDto): Response<ResponseFindEmailDto>?{
        return FindInfoService.getApi()?.findEmail(requestFindEmailDto = requestFindEmailDto)
    }

    override suspend fun findPw(requestFindPwDto: RequestFindPwDto): BaseResponse<ResponseFindPwDto>?{
        return FindInfoService.getApi()?.findPw(requestFindPwDto=requestFindPwDto)
    }
}