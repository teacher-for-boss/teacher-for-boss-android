package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.response.home.ResponseBossTalkPopularPostDto
import com.example.teacherforboss.data.model.response.home.ResponseBossTalkPopularPostDto.HotPostList
import com.example.teacherforboss.util.base.BaseResponse

interface HomeRemoteDataSource {
    suspend fun getBossTalkPopularPost(): BaseResponse<ResponseBossTalkPopularPostDto>
}