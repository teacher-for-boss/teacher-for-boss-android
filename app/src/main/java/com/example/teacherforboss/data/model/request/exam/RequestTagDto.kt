package com.example.teacherforboss.data.model.request.exam

import com.google.gson.annotations.SerializedName

data class RequestTagDto(
    @SerializedName("examCategoryId")
    val examCategoryId:Long,
)