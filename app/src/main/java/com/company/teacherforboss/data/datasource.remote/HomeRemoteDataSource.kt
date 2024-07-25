package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.response.home.ResponseBossTalkPopularPostDto
import com.company.teacherforboss.data.model.response.home.ResponseTeacherTalkPopularPostDto
import com.company.teacherforboss.data.model.response.home.ResponseWeeklyBestTeacherDto
import com.company.teacherforboss.util.base.BaseResponse

interface HomeRemoteDataSource {
    suspend fun getBossTalkPopularPost(): BaseResponse<ResponseBossTalkPopularPostDto>

    suspend fun getTeacherTalkPopularPost(): BaseResponse<ResponseTeacherTalkPopularPostDto>

    suspend fun getWeeklyBestTeacher(): BaseResponse<ResponseWeeklyBestTeacherDto>
}
