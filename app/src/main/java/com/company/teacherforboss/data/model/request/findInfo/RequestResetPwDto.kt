package com.company.teacherforboss.data.model.request.findInfo

import com.google.gson.annotations.SerializedName

data class RequestResetPwDto(
    @SerializedName("memberId")
    val memberId:Long,
    @SerializedName("password")
    val password:String,
    @SerializedName("rePassword")
    val rePassword:String,
)
