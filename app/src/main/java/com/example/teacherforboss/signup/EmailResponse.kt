package com.example.teacherforboss.signup

import com.example.teacherforboss.login.LoginResponse
import java.time.LocalDateTime
import java.util.Date

data class EmailResponse (
    @SerializedName("code")
    var code:String,

    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var `result`:Result
) {
    data class Result (
        @SerializedName("emailAuthId")
        var accessToken:Int,

        @SerializedName("createdAt")
        var refreshToken:LocalDateTime,

        @SerializedName("email")
        var email:String,

        @SerializedName("purpose")
        var purpose:String
    )
}