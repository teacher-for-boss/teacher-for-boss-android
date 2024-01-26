package com.example.teacherforboss.presentation.ui.auth.signup.api

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class EmailResponse (
    @SerializedName("code")
    var code:String,

    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var result: Result
) {
    data class Result (
        @SerializedName("emailAuthId")
        var emailAuthId:Long?,

        @SerializedName("createdAt")
        var createdAt: LocalDateTime?,

        @SerializedName("email")
        var email:String?,

        @SerializedName("purpose")
        var purpose:String?
    )
}