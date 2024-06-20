package com.example.teacherforboss.data.model.request.community.boss

import com.google.gson.annotations.SerializedName

data class RequestBossUploadPostDto (
    @SerializedName("title") val title:String,
    @SerializedName("content") val content:String,
    @SerializedName("imageUrlList") val imageUrlList:List<String>,
    @SerializedName("hashtagList") val hashtagList:List<String>

)