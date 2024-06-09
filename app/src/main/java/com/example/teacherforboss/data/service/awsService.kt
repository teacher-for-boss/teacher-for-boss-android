package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.model.response.aws.ResponsePresignedUrlDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface awsService {
    @GET("/s3/presigned-url")
    suspend fun getPresingedUrl(
        @Query("type") type:String,
        @Query("id") id:Long,
        @Query("imageCount") imageCount:Int,
    ):BaseResponse<ResponsePresignedUrlDto>
}