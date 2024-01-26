package com.example.teacherforboss.presentation.ui.auth.signup.api

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class SignupResponse(
    @SerializedName("code")
    var code:String,

    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var `result`: Result
){
    data class Result (
        @SerializedName("memberId")
        var memberId:Long,
        @SerializedName("createdAt")
        var createdAt: LocalDateTime
    )
}
