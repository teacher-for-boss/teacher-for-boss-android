package com.example.teacherforboss.data.datasource.remote

import com.example.teacherforboss.data.model.response.home.ResponseBossTalkPopularPostDto
import com.example.teacherforboss.data.model.response.home.ResponseTeacherTalkPopularPostDto
import com.example.teacherforboss.data.model.response.home.ResponseWeeklyBestTeacherDto
import com.example.teacherforboss.util.base.BaseResponse

interface HomeRemoteDataSource {
    suspend fun getBossTalkPopularPost(): BaseResponse<ResponseBossTalkPopularPostDto>

    suspend fun getTeacherTalkPopularPost(): BaseResponse<ResponseTeacherTalkPopularPostDto>

    suspend fun getWeeklyBestTeacher(): BaseResponse<ResponseWeeklyBestTeacherDto>
}
