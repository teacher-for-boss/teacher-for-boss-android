package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.model.response.home.ResponseBossTalkPopularPostDto
import com.example.teacherforboss.data.model.response.home.ResponseBossTalkPopularPostDto.HotPostList
import com.example.teacherforboss.data.model.response.home.ResponseTeacherTalkPopularPostDto
import com.example.teacherforboss.data.model.response.home.ResponseWeeklyBestTeacherDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET

interface HomeService {
    @GET("home/hot-posts")
    suspend fun getBossTalkPopularPost(): BaseResponse<ResponseBossTalkPopularPostDto>

    @GET("home/hot-questions")
    suspend fun getTeacherTalkPopularPost(): BaseResponse<ResponseTeacherTalkPopularPostDto>

    @GET("home/hot-teachers")
    suspend fun getWeeklyBestTeacher(): BaseResponse<ResponseWeeklyBestTeacherDto>
}