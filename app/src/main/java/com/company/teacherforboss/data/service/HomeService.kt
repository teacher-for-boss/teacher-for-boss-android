package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.response.home.ResponseBossTalkPopularPostDto
import com.company.teacherforboss.data.model.response.home.ResponseTeacherTalkPopularPostDto
import com.company.teacherforboss.data.model.response.home.ResponseWeeklyBestTeacherDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET

interface HomeService {
    @GET("home/hot-posts")
    suspend fun getBossTalkPopularPost(): BaseResponse<ResponseBossTalkPopularPostDto>

    @GET("home/hot-questions")
    suspend fun getTeacherTalkPopularPost(): BaseResponse<ResponseTeacherTalkPopularPostDto>

    @GET("home/hot-teachers")
    suspend fun getWeeklyBestTeacher(): BaseResponse<ResponseWeeklyBestTeacherDto>
}