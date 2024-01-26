package com.example.teacherforboss.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("code")
    var code:Int,

    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var `result`:Result
){
    data class Result(
        @SerializedName("accessToken")
        var accessToken:String,

        @SerializedName("refreshToken")
        var refreshToken:String,

//        @SerializedName("login")
//        var login:String
    )
}

