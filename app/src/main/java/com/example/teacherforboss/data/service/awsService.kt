package com.example.teacherforboss.data.service

import com.example.teacherforboss.data.model.response.aws.ResponsePresignedUrlDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.Call
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Url

interface awsService {
    @GET("s3/presigned-url?")
    suspend fun getPresingedUrl(
        @Query("uuid") uuid:String?,
        @Query("lastIndex") lastIndex:Int,
        @Query("imageCount") imageCount:Int,
        @Query("origin") origin:String
    ):BaseResponse<ResponsePresignedUrlDto>

    @PUT
    fun uploadImg(@Url url:String,@Body file:RequestBody,
                  @Header("Content-Type") fileType:String):Call<Void>

}