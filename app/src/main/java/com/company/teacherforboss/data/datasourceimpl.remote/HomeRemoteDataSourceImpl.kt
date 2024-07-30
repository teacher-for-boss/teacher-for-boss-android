package com.company.teacherforboss.data.datasourceimpl.remote

import com.company.teacherforboss.data.datasource.remote.HomeRemoteDataSource
import com.company.teacherforboss.data.model.response.home.ResponseBossTalkPopularPostDto
import com.company.teacherforboss.data.model.response.home.ResponseTeacherTalkPopularPostDto
import com.company.teacherforboss.data.model.response.home.ResponseWeeklyBestTeacherDto
import com.company.teacherforboss.data.service.HomeService
import com.company.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(
    private val homeService: HomeService,
) : HomeRemoteDataSource {

    override suspend fun getBossTalkPopularPost(): BaseResponse<ResponseBossTalkPopularPostDto> =
        homeService.getBossTalkPopularPost()

    override suspend fun getTeacherTalkPopularPost(): BaseResponse<ResponseTeacherTalkPopularPostDto> =
        homeService.getTeacherTalkPopularPost()

    override suspend fun getWeeklyBestTeacher(): BaseResponse<ResponseWeeklyBestTeacherDto> =
        homeService.getWeeklyBestTeacher()
}
