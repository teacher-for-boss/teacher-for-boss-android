package com.company.teacherforboss.data.repositoryImpl

import com.company.teacherforboss.data.model.request.findInfo.RequestFindEmailDto
import com.company.teacherforboss.data.model.request.findInfo.RequestFindPwDto
import com.company.teacherforboss.data.model.request.findInfo.RequestResetPwDto
import com.company.teacherforboss.data.model.response.findInfo.ResponseFindEmailDto
import com.company.teacherforboss.data.model.response.findInfo.ResponseFindPwDto
import com.company.teacherforboss.data.model.response.findInfo.ResponseResetPwDto
import com.company.teacherforboss.data.service.FindInfoService
import com.company.teacherforboss.domain.repository.FindInfoRepository
import com.company.teacherforboss.util.base.BaseResponse

class FindInfoRepositoryImpl:FindInfoRepository {
    override suspend fun findEmail(requestFindEmailDto: RequestFindEmailDto): BaseResponse<ResponseFindEmailDto>?{
        return FindInfoService.getApi()?.findEmail(requestFindEmailDto = requestFindEmailDto)
    }

    override suspend fun findPw(requestFindPwDto: RequestFindPwDto): BaseResponse<ResponseFindPwDto>?{
        return FindInfoService.getApi()?.findPw(requestFindPwDto=requestFindPwDto)
    }

    override suspend fun resetPw(requestResetPwDto: RequestResetPwDto): BaseResponse<ResponseResetPwDto>?{
        return FindInfoService.getApi()?.resetPw(requestResetPw = requestResetPwDto)
    }
}