package com.example.teacherforboss.presentation.ui.auth.login

import com.google.gson.annotations.SerializedName

interface loginInterface {
    var code:Int
    var isSuccess:Boolean
    var message:String
    var result: Result
    data class Result(
        var accessToken:String,
        var refreshToken:String,
        var email:String? //social시 사용
    )

}