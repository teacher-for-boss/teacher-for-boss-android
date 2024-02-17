package com.example.teacherforboss.data.model.response.findInfo

import com.google.gson.annotations.SerializedName

data class ResponseResetPwDto(
    @SerializedName("memberId")
    val memberId:Long,
    @SerializedName("isChanged")
    val isChanged:Boolean
)
