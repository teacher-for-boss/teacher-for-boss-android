package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.HomeRemoteDataSource
import com.example.teacherforboss.data.model.response.home.ResponseBossTalkPopularPostDto
import com.example.teacherforboss.data.model.response.home.ResponseTeacherTalkPopularPostDto
import com.example.teacherforboss.data.service.HomeService
import com.example.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(
    private val homeService: HomeService,
) : HomeRemoteDataSource {

    override suspend fun getBossTalkPopularPost(): BaseResponse<ResponseBossTalkPopularPostDto> =
        homeService.getBossTalkPopularPost()

    override suspend fun getTeacherTalkPopularPost(): BaseResponse<ResponseTeacherTalkPopularPostDto> =
        homeService.getTeacherTalkPopularPost()

}
